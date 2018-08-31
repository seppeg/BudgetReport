package com.cegeka.project.service;

import com.cegeka.project.infrastructure.UnexistingResourceException;
import com.cegeka.project.project.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.MessageChannel;

import static com.cegeka.project.project.ProjectTestBuilder.project;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
class ProjectServiceTest {

    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private MessageChannel messageChannel;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @BeforeEach
    void setUp() {
        projectService = new ProjectService(projectRepository, new ProjectRAssembler());
        when(projectRepository.save(isA(Project.class))).thenAnswer(returnsFirstArg());
    }

    @Test
    void createProject() throws ProjectAlreadyExistsException {
        CreateProjectR project = new CreateProjectR("desc", 5);

        projectService.createProject(project);

        ArgumentCaptor<Project> projectArgumentCaptor = ArgumentCaptor.forClass(Project.class);
        verify(projectRepository).save(projectArgumentCaptor.capture());
        Project savedProject = projectArgumentCaptor.getValue();
        assertThat(savedProject.getBudget()).isEqualTo(5D);
        assertThat(savedProject.getName()).isEqualTo("desc");
    }

    @Test
    void updateProject() throws UnexistingResourceException {
        Project project = project()
                .name("test")
                .budget(5)
                .build();
        when(projectRepository.findById(project.getId())).thenReturn(of(project));
        CreateProjectR projectR = new CreateProjectR("test1", 6);

        projectService.updateProject(project.getId(), projectR);

        assertThat(project.getName()).isEqualTo("test1");
        assertThat(project.getBudget()).isEqualTo(6D);
    }
}