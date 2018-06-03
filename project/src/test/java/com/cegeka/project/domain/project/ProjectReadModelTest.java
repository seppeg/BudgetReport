package com.cegeka.project.domain.project;

import com.cegeka.project.booking.BookingCreated;
import com.cegeka.project.event.BookingDeletedTestBuilder;
import com.cegeka.project.project.Project;
import com.cegeka.project.project.ProjectReadModel;
import com.cegeka.project.project.ProjectRepository;
import com.cegeka.project.workorder.WorkOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static com.cegeka.project.domain.ProjectTestBuilder.project;
import static com.cegeka.project.event.BookingCreatedTestBuilder.bookingCreated;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectReadModelTest {

    private static final String JAVA_GUILD_WORKORDER = "COCFL871.004";

    private ProjectReadModel projectReadModel;

    @Mock
    private ProjectRepository projectRepository;

    @BeforeEach
    void setUp() {
        projectReadModel = new ProjectReadModel(projectRepository);
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
        projectReadModel.on(bookingCreated);

        assertThat(project.getHoursSpent()).isEqualTo(2);
    }

    @Test
    void onBookingCreated_anotherBookingAtSameDateExists() {
        WorkOrder workOrder = new WorkOrder(JAVA_GUILD_WORKORDER);
        Project project = project()
                .hoursSpent(5)
                .workorder(workOrder)
                .build();
        when(projectRepository.findByWorkOrdersWorkOrderContains(JAVA_GUILD_WORKORDER)).thenReturn(of(project));

        projectReadModel.on(bookingCreated()
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

        projectReadModel.on(BookingDeletedTestBuilder.bookingDeleted()
                .hours(2)
                .workorder(JAVA_GUILD_WORKORDER)
                .build());

        assertThat(project.getHoursSpent()).isEqualTo(8);
    }

    @Test
    void onBookingDeleted_DoesNothingWhenNoProjectFound() {
        when(projectRepository.findByWorkOrdersWorkOrderContains(JAVA_GUILD_WORKORDER)).thenReturn(empty());

        projectReadModel.on(BookingDeletedTestBuilder.bookingDeleted()
                .hours(2)
                .workorder(JAVA_GUILD_WORKORDER)
                .build());
    }
}