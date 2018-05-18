package com.cegeka.project.service;

import com.cegeka.project.domain.Project;
import com.cegeka.project.domain.ProjectRepository;
import com.cegeka.project.domain.ProjectTestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.cegeka.project.domain.ProjectTestBuilder.project;
import static com.cegeka.project.service.BookingCreatedTestBuilder.bookingCreated;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    private static final String JAVA_GUILD_WORKORDER = "COCFL871.004";
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @BeforeEach
    void setUp() {
        projectService = new ProjectService(projectRepository);
    }

    @Test
    void updateHoursSpent() {
        projectService.updateHoursSpent(bookingCreated()
                .hours(2)
                .workorder(JAVA_GUILD_WORKORDER)
                .build());

        ArgumentCaptor<Project> captor = ArgumentCaptor.forClass(Project.class);
        verify(projectRepository).save(captor.capture());
        assertThat(captor.getValue().getHoursSpent()).isEqualTo(2);
    }

    @Test
    void removeHoursSpent() {
        Project project = project()
                .hoursSpent(10)
                .build();

        when(projectRepository.findByWorkorder(JAVA_GUILD_WORKORDER)).thenReturn(of(project));

        projectService.deleteHoursSpent(BookingDeletedTestBuilder.bookingDeleted()
                .hours(2)
                .workorder(JAVA_GUILD_WORKORDER)
                .build());

        assertThat(project.getHoursSpent()).isEqualTo(8);
    }

    @Test
    void removeHoursSpent_DoesNothingWhenNoProjectFound() {
        when(projectRepository.findByWorkorder(JAVA_GUILD_WORKORDER)).thenReturn(empty());

        projectService.deleteHoursSpent(BookingDeletedTestBuilder.bookingDeleted()
                .hours(2)
                .workorder(JAVA_GUILD_WORKORDER)
                .build());
    }
}