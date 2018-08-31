package com.cegeka.project.booking;

import com.cegeka.project.booking.monthly.MonthlyProjectBookingService;
import com.cegeka.project.project.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.cegeka.timeprovider.Timeprovider.freezeTime;
import static java.util.Optional.of;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    private BookingController bookingController;

    @Mock
    private ProjectService projectService;

    @Mock
    private MonthlyProjectBookingService monthlyProjectBookingService;


    @BeforeEach
    void setUp() {
        bookingController = new BookingController(projectService, monthlyProjectBookingService);
    }

    @Test
    void getMonthlyProjectBookings() {
        UUID projectId = UUID.randomUUID();
        when(projectService.getProjectName(projectId)).thenReturn(of("project"));

        bookingController.getMonthlyProjectBookings(projectId, "2018-01", "2018-02");

        verify(monthlyProjectBookingService).fetchMonthlyProjectBookingView("project", LocalDate.of(2018, 1, 1), LocalDate.of(2018, 2, 1));
    }

    @Test
    void getMonthlyProjectBookings_noBoundaryDate_defaultsToCurrentMonth() {
        freezeTime(LocalDateTime.of(2018, 1, 1, 0, 0));
        UUID projectId = UUID.randomUUID();
        when(projectService.getProjectName(projectId)).thenReturn(of("project"));

        bookingController.getMonthlyProjectBookings(projectId, null, null);

        verify(monthlyProjectBookingService).fetchMonthlyProjectBookingView("project", LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 1));
    }

    @Test
    void getMonthlyProjectBookings_boundaryDateInISOFormat() {
        UUID projectId = UUID.randomUUID();
        when(projectService.getProjectName(projectId)).thenReturn(of("project"));

        bookingController.getMonthlyProjectBookings(projectId, "2018-01-01", "2018-01-05");

        verify(monthlyProjectBookingService).fetchMonthlyProjectBookingView("project", LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 1));
    }

    @Test
    void getMonthlyProjectBookings_invalidBoundaries_defaultsToCurrentMonth() {
        freezeTime(LocalDateTime.of(2018, 1, 1, 0, 0));
        UUID projectId = UUID.randomUUID();
        when(projectService.getProjectName(projectId)).thenReturn(of("project"));

        bookingController.getMonthlyProjectBookings(projectId, "", "bla");

        verify(monthlyProjectBookingService).fetchMonthlyProjectBookingView("project", LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 1));
    }


}
