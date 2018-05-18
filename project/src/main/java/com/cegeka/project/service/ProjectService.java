package com.cegeka.project.service;

import com.cegeka.project.controller.ProjectR;
import com.cegeka.project.domain.Project;
import com.cegeka.project.domain.ProjectRepository;
import com.cegeka.project.domain.Workorder;
import com.cegeka.project.event.BookingCreated;
import com.cegeka.project.event.BookingDeleted;
import com.cegeka.project.event.ProjectCreated;
import com.cegeka.project.event.ProjectCreatedBuilder;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Log4j2
@Service
@Transactional
@AllArgsConstructor
public class ProjectService {

    private ProjectRepository projectRepository;
    private ProjectStreams projectStreams;

    @StreamListener(ProjectStreams.INPUT)
    public void updateHoursSpent(@Payload BookingCreated bookingCreated) {
        Optional<Project> project = projectRepository.findByWorkordersContains(bookingCreated.getWorkorder());
        project.ifPresent(p -> addHoursSpent(p, bookingCreated.getHours()));
    }

    private void addHoursSpent(Project p, double hours) {
        p.addHoursSpent(hours);
        log.info("updated hours spent +");
    }

    @StreamListener(value = ProjectStreams.INPUT, condition = "headers['type']=='BookingDeleted'")
    public void updateHoursSpent(@Payload BookingDeleted bookingDeleted) {
        Optional<Project> project = projectRepository.findByWorkordersContains(bookingDeleted.getWorkorder());
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
        ProjectCreated projectCreated = ProjectCreatedBuilder.projectCreated()
                .id(UUID.randomUUID())
                .workorder(getWorkorder(projectR))
                .budget(projectR.getBudget())
                .description(projectR.getDescription())
                .build();
        projectRepository.save(new Project(projectCreated));
        raiseEvent(projectCreated);
    }

    private List<Workorder> getWorkorder(ProjectR projectR) {
        return projectR.getWorkorder().stream().map(workorderR -> new Workorder(workorderR.getWorkorder())).collect(toList());
    }

    private void raiseEvent(Object event) {
        log.info("Raising event "+event);
        MessageChannel messageChannel = projectStreams.outboundProjects();
        Message<Object> message = MessageBuilder.withPayload(event).setHeader("type", event.getClass().getSimpleName()).build();
        messageChannel.send(message);
    }
}
