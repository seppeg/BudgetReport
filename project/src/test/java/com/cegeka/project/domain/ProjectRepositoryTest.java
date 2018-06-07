package com.cegeka.project.domain;

import com.cegeka.project.PersistenceTest;
import com.cegeka.project.project.Project;
import com.cegeka.project.project.ProjectRepository;
import com.cegeka.project.workorder.WorkOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.cegeka.project.domain.ProjectTestBuilder.project;
import static org.assertj.core.api.Assertions.assertThat;

class ProjectRepositoryTest  extends PersistenceTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void findByWorkOrdersWorkOrderContains() {
        Project project = project()
                .workorder(new WorkOrder("COCFL871.004"))
                .build();
        projectRepository.save(project);

        Optional<Project> result = projectRepository.findByWorkOrdersWorkOrderContains("COCFL871.004");

        assertThat(result).contains(project);
    }
}