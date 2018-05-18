package com.cegeka.project.service;

import com.cegeka.project.domain.Project;
import com.cegeka.project.domain.ProjectRepository;
import com.cegeka.project.event.BookingCreated;
import com.cegeka.project.event.BookingDeleted;
import com.cegeka.project.event.BookingEvent;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
@AllArgsConstructor
@Log4j2
public class ProjectService {

    private ProjectRepository projectRepository;

    @StreamListener(value = ProjectStreams.INPUT, condition = "headers['type']=='BookingCreated'")
    public void updateHoursSpent(@Payload BookingCreated bookingCreated) {
        Project project = getProject(bookingCreated);
        project.addHoursSpent(bookingCreated.getHours());
        projectRepository.save(project);
        log.info("updated hours spent +");
    }

    @StreamListener(value = ProjectStreams.INPUT, condition = "headers['type']=='BookingDeleted'")
    public void updateHoursSpent(@Payload BookingDeleted bookingDeleted) {
        Project project = getProject(bookingDeleted);
        project.subtractHoursSpent(bookingDeleted.getHours());
        projectRepository.save(project);
        log.info("updated hours spent -");
    }

    //TODO create projects in flyway and not create projects ad hoc
    private Project getProject(BookingEvent bookingEvent) {
        return projectRepository.findByWorkorder(bookingEvent.getWorkorder())
                .orElse(new Project(bookingEvent.getWorkorder(), "Java guild", 1000));
    }

    @StreamListener(ProjectStreams.INPUT)
    public void deleteHoursSpent(@Payload BookingDeleted bookingDeleted) {
        projectRepository.findByWorkorder(bookingDeleted.getWorkorder())
                .ifPresent(project -> project.removeHoursSpent(bookingDeleted.getHours()));
    }


    public Collection<Project> getAllProjects(){
        return projectRepository.findAll();
    }

}
