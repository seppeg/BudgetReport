package com.cegeka.project.service;

import com.cegeka.project.controller.ProjectR;
import com.cegeka.project.domain.Project;
import com.cegeka.project.domain.ProjectRepository;
import com.cegeka.project.event.BookingCreated;
import com.cegeka.project.event.BookingDeleted;
import com.cegeka.project.event.ProjectCreated;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static com.cegeka.project.event.ProjectCreatedBuilder.projectCreated;

@Service
@Transactional
@AllArgsConstructor
@Log4j2
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectStreams projectStreams;

    @StreamListener(value = ProjectStreams.INPUT, condition = "headers['type']=='BookingCreated'")
    public void updateHoursSpent(@Payload BookingCreated bookingCreated) {
        Optional<Project> project = projectRepository.findByWorkorder(bookingCreated.getWorkorder());
        project.ifPresent(p -> addHoursSpent(p, bookingCreated.getHours()));
    }

    private void addHoursSpent(Project p, double hours) {
        p.addHoursSpent(hours);
        log.info("updated hours spent +");
    }

    @StreamListener(value = ProjectStreams.INPUT, condition = "headers['type']=='BookingDeleted'")
    public void updateHoursSpent(@Payload BookingDeleted bookingDeleted) {
        Optional<Project> project = projectRepository.findByWorkorder(bookingDeleted.getWorkorder());
        project.ifPresent(p -> removeHoursSpent(p, bookingDeleted.getHours()));
    }

    private void removeHoursSpent(Project p, double hours) {
        p.removeHoursSpent(hours);
        log.info("updated hours spent -");
    }

    public Collection<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public void createProject(ProjectR projectR) {
        ProjectCreated projectCreated = projectCreated()
                .id(UUID.randomUUID())
                .workorder(projectR.getWorkorder())
                .budget(projectR.getBudget())
                .description(projectR.getDescription())
                .build();
        projectRepository.save(new Project(projectCreated));
        raiseEvent(projectCreated);
    }

    private void raiseEvent(Object event) {
        log.info("Raising event "+event);
        MessageChannel messageChannel = projectStreams.outboundProjects();
        Message<Object> message = MessageBuilder.withPayload(event).setHeader("type", event.getClass().getSimpleName()).build();
        messageChannel.send(message);
    }
}
