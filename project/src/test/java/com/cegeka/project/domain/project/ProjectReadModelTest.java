package com.cegeka.project.domain.project;

import com.cegeka.project.booking.BookingCreated;
import com.cegeka.project.booking.BookingDeleted;
import com.cegeka.project.project.BookingEventProjectSpecificationMatcher;
import com.cegeka.project.project.Project;
import com.cegeka.project.project.ProjectReadModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static com.cegeka.project.event.BookingCreatedTestBuilder.bookingCreated;
import static com.cegeka.project.event.BookingDeletedTestBuilder.bookingDeleted;
import static com.cegeka.project.project.ProjectTestBuilder.project;
import static java.util.Collections.emptyList;
import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectReadModelTest {

    private static final String JAVA_GUILD_WORKORDER = "COCFL871.004";

    private ProjectReadModel projectReadModel;

    @Mock
    private BookingEventProjectSpecificationMatcher matcher;

    @BeforeEach
    void setUp() {
        projectReadModel = new ProjectReadModel(matcher);
    }

    @Test
    void onBookingCreated() {
        Project project = project()
                .hoursSpent(0)
                .build();
        BookingCreated bookingCreated = bookingCreated()
                .hours(2)
                .workorder(JAVA_GUILD_WORKORDER)
                .date(LocalDate.of(2018, 1, 1))
                .build();
        when(matcher.getProjectsMatchingEvent(bookingCreated)).thenReturn(singleton(project));

        projectReadModel.on(bookingCreated);

        assertThat(project.getHoursSpent()).isEqualTo(2);
    }

    @Test
    void onBookingDeleted() {
        Project project = project()
                .hoursSpent(10)
                .build();
        BookingDeleted bookingDeleted = bookingDeleted()
                .hours(2)
                .workorder(JAVA_GUILD_WORKORDER)
                .build();
        when(matcher.getProjectsMatchingEvent(bookingDeleted)).thenReturn(singleton(project));

        projectReadModel.on(bookingDeleted);

        assertThat(project.getHoursSpent()).isEqualTo(8);
    }

    @Test
    void onBookingDeleted_DoesNothingWhenNoProjectFound() {
        BookingDeleted bookingDeleted = bookingDeleted()
                .hours(2)
                .workorder(JAVA_GUILD_WORKORDER)
                .build();
        when(matcher.getProjectsMatchingEvent(bookingDeleted)).thenReturn(emptyList());

        projectReadModel.on(bookingDeleted);
    }
}