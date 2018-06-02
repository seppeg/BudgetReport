package com.cegeka.project.service;

import com.cegeka.project.controller.ProjectR;
import com.cegeka.project.controller.WorkOrderR;
import com.cegeka.project.domain.daybooking.DayBookingReadModel;
import com.cegeka.project.domain.project.Project;
import com.cegeka.project.domain.project.ProjectRepository;
import com.cegeka.project.domain.workorder.WorkOrder;
import com.cegeka.project.domain.workorder.WorkOrderRepository;
import com.cegeka.project.event.BookingCreated;
import com.cegeka.project.event.BookingDeletedTestBuilder;
import com.cegeka.project.event.ProjectCreated;
import com.cegeka.project.infrastructure.ProjectStreams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import java.time.LocalDate;
import java.util.Optional;

import static com.cegeka.project.controller.ProjectRTestBuilder.projectR;
import static com.cegeka.project.domain.ProjectTestBuilder.project;
import static com.cegeka.project.event.BookingCreatedTestBuilder.bookingCreated;
import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
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

    @Mock
    private DayBookingReadModel dayBookingReadModel;

    @Mock
    private WorkOrderRepository workOrderRepository;

    @BeforeEach
    void setUp() {
        projectService = new ProjectService(projectRepository, projectStreams, workOrderTracker, workOrderRepository, dayBookingReadModel);
        when(projectRepository.save(isA(Project.class))).thenAnswer(returnsFirstArg());
        when(workOrderRepository.save(isA(WorkOrder.class))).thenAnswer(returnsFirstArg());
    }

    @Test
    void createProject() throws ProjectAlreadyExistsException {
        when(projectStreams.outboundProjects()).thenReturn(messageChannel);
        when(workOrderRepository.findByWorkOrder("workorder")).thenReturn(Optional.of(new WorkOrder("workorder")));
        ProjectR project = projectR()
                .budget(5)
                .name("desc")
                .workorder(singletonList(new WorkOrderR("workorder")))
                .build();

        projectService.createProject(project);

        verify(workOrderTracker).trackWorkOrders(singletonList("workorder"));

        ArgumentCaptor<Project> projectArgumentCaptor = ArgumentCaptor.forClass(Project.class);
        verify(projectRepository).save(projectArgumentCaptor.capture());
        Project savedProject = projectArgumentCaptor.getValue();
        assertThat(savedProject.getBudget()).isEqualTo(5D);
        assertThat(savedProject.getName()).isEqualTo("desc");
        assertThat(savedProject.getWorkOrders()).extracting(WorkOrder::getWorkOrder).containsExactly("workorder");

        ArgumentCaptor<Message> messageArgumentCaptor = ArgumentCaptor.forClass(Message.class);
        verify(messageChannel).send(messageArgumentCaptor.capture());
        Object payload = messageArgumentCaptor.getValue().getPayload();
        assertThat(payload).isInstanceOf(ProjectCreated.class);
        ProjectCreated projectCreated = (ProjectCreated) payload;
        assertThat(projectCreated.getName()).isEqualTo("desc");
        assertThat(projectCreated.getBudget()).isEqualTo(5D);
        assertThat(projectCreated.getWorkOrders()).containsExactly("workorder");
    }

    @Test
    void onBookingCreated() {
        Project project = project()
                .hoursSpent(0)
                .workorder(new WorkOrder(JAVA_GUILD_WORKORDER))
                .build();

        when(projectRepository.findByWorkOrdersWorkOrderContains(JAVA_GUILD_WORKORDER)).thenReturn(of(project));

        BookingCreated bookingCreated = bookingCreated()
                .hours(2)
                .workorder(JAVA_GUILD_WORKORDER)
                .date(LocalDate.of(2018, 1, 1))
                .build();
        projectService.onBookingCreated(bookingCreated);

        assertThat(project.getHoursSpent()).isEqualTo(2);
        verify(dayBookingReadModel).on(bookingCreated);
    }

    @Test
    void onBookingCreated_anotherBookingAtSameDateExists() {
        WorkOrder workOrder = new WorkOrder(JAVA_GUILD_WORKORDER);
        Project project = project()
                .hoursSpent(5)
                .workorder(workOrder)
                .build();
        when(projectRepository.findByWorkOrdersWorkOrderContains(JAVA_GUILD_WORKORDER)).thenReturn(of(project));

        projectService.onBookingCreated(bookingCreated()
                .hours(2)
                .workorder(JAVA_GUILD_WORKORDER)
                .date(LocalDate.of(2018, 1, 1))
                .build());

        assertThat(project.getHoursSpent()).isEqualTo(7);
    }

    @Test
    void onBookingDeleted() {
        Project project = project()
                .hoursSpent(10)
                .build();

        when(projectRepository.findByWorkOrdersWorkOrderContains(JAVA_GUILD_WORKORDER)).thenReturn(of(project));

        projectService.onBookingDeleted(BookingDeletedTestBuilder.bookingDeleted()
                .hours(2)
                .workorder(JAVA_GUILD_WORKORDER)
                .build());

        assertThat(project.getHoursSpent()).isEqualTo(8);
    }

    @Test
    void onBookingDeleted_DoesNothingWhenNoProjectFound() {
        when(projectRepository.findByWorkOrdersWorkOrderContains(JAVA_GUILD_WORKORDER)).thenReturn(empty());

        projectService.onBookingDeleted(BookingDeletedTestBuilder.bookingDeleted()
                .hours(2)
                .workorder(JAVA_GUILD_WORKORDER)
                .build());
    }
}