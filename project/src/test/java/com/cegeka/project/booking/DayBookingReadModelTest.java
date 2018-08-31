package com.cegeka.project.booking;

import com.cegeka.project.project.BookingEventProjectSpecificationMatcher;
import com.cegeka.project.project.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static com.cegeka.project.event.BookingCreatedTestBuilder.bookingCreated;
import static com.cegeka.project.event.BookingDeletedTestBuilder.bookingDeleted;
import static com.cegeka.project.project.ProjectTestBuilder.project;
import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DayBookingReadModelTest {

    private DayBookingReadModel readModel;

    @Mock
    private DayBookingRepository dayBookingRepository;

    @Mock
    private BookingEventProjectSpecificationMatcher matcher;


    @BeforeEach
    void setUp() {
        readModel = new DayBookingReadModel(matcher, dayBookingRepository);
    }

    @Test
    void onBookingCreated_noBookingsAtDate_projectExists() {
        Project project = project().build();
        BookingCreated event = bookingCreated()
                .date(LocalDate.of(2018, 1, 1))
                .workorder("workOrder")
                .hours(5D)
                .description("desc")
                .employee("1")
                .build();
        when(matcher.getProjectsMatchingEvent(event)).thenReturn(singleton(project));

        readModel.on(event);

        verify(dayBookingRepository).save(refEq(new DayBooking(LocalDate.of(2018, 1, 1), project.getId(), 5D), "id"));
    }

    @Test
    void onBookingCreated_bookingsAtDate_projectExists() {
        BookingCreated event = bookingCreated()
                .date(LocalDate.of(2018, 1, 1))
                .workorder("workOrder")
                .hours(5D)
                .description("desc")
                .employee("1")
                .build();
        Project project = project().build();
        when(matcher.getProjectsMatchingEvent(event)).thenReturn(singleton(project));
        DayBooking dayBooking = new DayBooking(LocalDate.of(2018, 1, 1), project.getId(), 5D);
        when(dayBookingRepository.findByDateAndProjectId(LocalDate.of(2018, 1, 1), project.getId())).thenReturn(Optional.of(dayBooking));

        readModel.on(event);

        assertThat(dayBooking.getHours()).isEqualTo(10D);
    }

    @Test
    void onBookingDeleted_bookingsAtDate_subtractsBookingHours() {
        BookingDeleted event = bookingDeleted()
                .date(LocalDate.of(2018, 1, 1))
                .workorder("workOrder")
                .hours(2D)
                .description("desc")
                .employee("1")
                .build();
        Project project = project().build();
        when(matcher.getProjectsMatchingEvent(event)).thenReturn(singleton(project));
        DayBooking dayBooking = new DayBooking(LocalDate.of(2018, 1, 1), project.getId(), 5D);
        when(dayBookingRepository.findByDateAndProjectId(LocalDate.of(2018, 1, 1), project.getId())).thenReturn(Optional.of(dayBooking));

        readModel.on(event);

        assertThat(dayBooking.getHours()).isEqualTo(3D);
    }

    @Test
    void onBookingDeleted_noBookingsAtDate_doesNothing() {
        BookingDeleted event = bookingDeleted()
                .date(LocalDate.of(2018, 1, 1))
                .workorder("workOrder")
                .hours(2D)
                .description("desc")
                .employee("1")
                .build();
        Project project = project().build();
        when(matcher.getProjectsMatchingEvent(event)).thenReturn(singleton(project));
        when(dayBookingRepository.findByDateAndProjectId(LocalDate.of(2018, 1, 1), project.getId())).thenReturn(Optional.empty());

        readModel.on(event);

        verifyNoMoreInteractions(dayBookingRepository);
    }

}