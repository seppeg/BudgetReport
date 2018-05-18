package com.cegeka.project.service;

import com.cegeka.project.controller.ProjectR;
import com.cegeka.project.domain.Project;
import com.cegeka.project.domain.ProjectRepository;
import com.cegeka.project.event.BookingCreated;
import com.cegeka.project.event.BookingDeleted;
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
    public void updateHoursSpent(@Payload BookingCreated bookingCreated) throws InvalidWorkOrderException {
        Project project = projectRepository.findByWorkorder(bookingCreated.getWorkorder())
                .orElseThrow(() -> new InvalidWorkOrderException("No project for workorder " + bookingCreated.getWorkorder()));
        project.addHoursSpent(bookingCreated.getHours());
        log.info("updated hours spent +");
    }

    @StreamListener(value = ProjectStreams.INPUT, condition = "headers['type']=='BookingDeleted'")
    public void updateHoursSpent(@Payload BookingDeleted bookingDeleted) throws InvalidWorkOrderException {
        Project project = projectRepository.findByWorkorder(bookingDeleted.getWorkorder())
                .orElseThrow(() -> new InvalidWorkOrderException("No project for workorder " + bookingDeleted.getWorkorder()));
        project.removeHoursSpent(bookingDeleted.getHours());
        log.info("updated hours spent -");
    }

    public Collection<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public void createProject(ProjectR projectR) {
        Project project = new Project(projectR.getWorkorder(), projectR.getDescription(), projectR.getBudget());
        projectRepository.save(project);
        log.info(() -> "created project " + project);
    }
}
