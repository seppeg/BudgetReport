package com.cegeka.project.service;

import com.cegeka.project.domain.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @StreamListener(ProjectStreams.INPUT)
    public void updateHoursSpent(@Payload ProjectTO projectTO) {
        projectRepository.findByWorkorder(projectTO.getWorkorder())
                .ifPresent(p -> p.addHoursSpent(projectTO.getHours()));
    }

}
