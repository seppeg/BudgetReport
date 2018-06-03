package com.cegeka.project.domain;

import com.cegeka.project.infrastructure.ZookeeperFacade;
import com.cegeka.project.project.Project;
import com.cegeka.project.project.ProjectRepository;
import com.cegeka.project.workorder.WorkOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.cegeka.project.domain.ProjectTestBuilder.project;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.cloud.zookeeper.enabled=false")
@ExtendWith(SpringExtension.class)
@Transactional
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @MockBean
    private ZookeeperFacade zookeeperFacade;

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