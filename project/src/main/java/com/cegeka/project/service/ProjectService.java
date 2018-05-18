package com.cegeka.project.service;

import com.cegeka.project.domain.Project;
import com.cegeka.project.domain.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
@AllArgsConstructor
public class ProjectService {

    private ProjectRepository projectRepository;

    @StreamListener(ProjectStreams.INPUT)
    public void updateHoursSpent(@Payload BookingCreated bookingCreated) {
        Project project = projectRepository.findByWorkorder(bookingCreated.getWorkorder())
                .orElse(new Project(bookingCreated.getWorkorder(), "Java guild", 1000));
        project.addHoursSpent(bookingCreated.getHours());
        projectRepository.save(project);
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
