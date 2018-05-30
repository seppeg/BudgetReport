package com.cegeka.project.service;

import com.cegeka.project.controller.ProjectR;
import com.cegeka.project.controller.WorkorderR;
import com.cegeka.project.domain.Project;
import com.cegeka.project.domain.ProjectRepository;
import com.cegeka.project.domain.Workorder;
import com.cegeka.project.event.BookingDeletedTestBuilder;
import com.cegeka.project.event.ProjectCreated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import static com.cegeka.project.controller.ProjectRTestBuilder.projectR;
import static com.cegeka.project.domain.ProjectTestBuilder.project;
import static com.cegeka.project.event.BookingCreatedTestBuilder.bookingCreated;
import static java.util.Collections.singletonList;
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

    @Mock
    private ProjectStreams projectStreams;

    @Mock
    private MessageChannel messageChannel;

    @Mock
    private WorkOrderTracker workOrderTracker;

    @BeforeEach
    void setUp() {
        projectService = new ProjectService(projectRepository, projectStreams, workOrderTracker);
    }

    @Test
    void createProject() {
        when(projectStreams.outboundProjects()).thenReturn(messageChannel);
        ProjectR project = projectR()
                .budget(5)
                .description("desc")
                .workorder(singletonList(new WorkorderR("workorder")))
                .build();

        projectService.createProject(project);

        verify(workOrderTracker).trackWorkOrders(singletonList("workorder"));

        ArgumentCaptor<Project> projectArgumentCaptor = ArgumentCaptor.forClass(Project.class);
        verify(projectRepository).save(projectArgumentCaptor.capture());
        Project savedProject = projectArgumentCaptor.getValue();
        assertThat(savedProject.getBudget()).isEqualTo(5D);
        assertThat(savedProject.getDescription()).isEqualTo("desc");
        assertThat(savedProject.getWorkorders()).extracting(Workorder::getWorkorder).containsExactly("workorder");

        ArgumentCaptor<Message> messageArgumentCaptor = ArgumentCaptor.forClass(Message.class);
        verify(messageChannel).send(messageArgumentCaptor.capture());
        Object payload = messageArgumentCaptor.getValue().getPayload();
        assertThat(payload).isInstanceOf(ProjectCreated.class);
        ProjectCreated projectCreated = (ProjectCreated) payload;
        assertThat(projectCreated.getDescription()).isEqualTo("desc");
        assertThat(projectCreated.getBudget()).isEqualTo(5D);
        assertThat(projectCreated.getWorkorders()).extracting(Workorder::getWorkorder).containsExactly("workorder");
    }

    @Test
    void updateHoursSpent() {
        Project project = project()
                .hoursSpent(0)
                .build();

        when(projectRepository.findByWorkordersWorkorderContains(JAVA_GUILD_WORKORDER)).thenReturn(of(project));

        projectService.updateHoursSpent(bookingCreated()
                .hours(2)
                .workorder(JAVA_GUILD_WORKORDER)
                .build());

        assertThat(project.getHoursSpent()).isEqualTo(2);
    }

    @Test
    void removeHoursSpent() {
        Project project = project()
                .hoursSpent(10)
                .build();

        when(projectRepository.findByWorkordersWorkorderContains(JAVA_GUILD_WORKORDER)).thenReturn(of(project));

        projectService.updateHoursSpent(BookingDeletedTestBuilder.bookingDeleted()
                .hours(2)
                .workorder(JAVA_GUILD_WORKORDER)
                .build());

        assertThat(project.getHoursSpent()).isEqualTo(8);
    }

    @Test
    void removeHoursSpent_DoesNothingWhenNoProjectFound() {
        when(projectRepository.findByWorkordersWorkorderContains(JAVA_GUILD_WORKORDER)).thenReturn(empty());

        projectService.updateHoursSpent(BookingDeletedTestBuilder.bookingDeleted()
                .hours(2)
                .workorder(JAVA_GUILD_WORKORDER)
                .build());
    }
}