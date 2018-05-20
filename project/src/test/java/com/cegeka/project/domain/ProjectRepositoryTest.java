package com.cegeka.project.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.cegeka.project.domain.ProjectTestBuilder.project;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void findByWorkordersWorkorderContains() {
        Project project = project()
                .workorder(new Workorder("COCFL871.004"))
                .build();
        projectRepository.save(project);

        Optional<Project> result = projectRepository.findByWorkordersWorkorderContains("COCFL871.004");

        assertThat(result).contains(project);
    }
}