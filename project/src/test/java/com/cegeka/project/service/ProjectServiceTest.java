package com.cegeka.project.service;

import com.cegeka.project.domain.Project;
import com.cegeka.project.domain.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.cegeka.project.service.ProjectTOTestBuilder.projectTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class ProjectServiceTest {

    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @BeforeEach
    void setUp() {
        projectService = new ProjectService(projectRepository);
    }

    @Test
    void updateHoursSpent() {
        projectService.updateHoursSpent(projectTO()
                .hours(2)
                .workorder(ProjectService.JAVA_GUILD_WORKORDER)
                .build());

        ArgumentCaptor<Project> captor = ArgumentCaptor.forClass(Project.class);
        verify(projectRepository).save(captor.capture());
        assertThat(captor.getValue().getHoursSpent()).isEqualTo(2);
    }
}