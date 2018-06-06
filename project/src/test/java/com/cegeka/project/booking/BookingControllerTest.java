package com.cegeka.project.booking;

import com.cegeka.project.booking.monthly.MonthlyWorkOrderBookingService;
import com.cegeka.project.booking.yearly.YearlyWorkOrderBookingService;
import com.cegeka.project.project.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.UUID;

import static com.cegeka.timeprovider.Timeprovider.freezeTime;
import static java.util.Collections.singleton;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    private BookingController bookingController;

    @Mock
    private ProjectService projectService;

    @Mock
    private MonthlyWorkOrderBookingService monthlyWorkOrderBookingService;

    @Mock
    private YearlyWorkOrderBookingService yearlyWorkOrderBookingService;

    @BeforeEach
    void setUp() {
        bookingController = new BookingController(projectService, monthlyWorkOrderBookingService, yearlyWorkOrderBookingService);
    }

    @Test
    void getMonthlyProjectBookings() {
        UUID projectId = UUID.randomUUID();
        when(projectService.findProjectWorkOrders(projectId)).thenReturn(singleton("workorder"));

        bookingController.getMonthlyProjectBookings(projectId, "2018-01", "2018-02");

        verify(monthlyWorkOrderBookingService).fetchMonthlyWorkOrdersBookingView(singleton("workorder"), LocalDate.of(2018, 1, 1), LocalDate.of(2018, 2, 1));
    }

    @Test
    void getMonthlyProjectBookings_noBoundaryDate_defaultsToCurrentMonth() {
        freezeTime(LocalDateTime.of(2018, 1, 1, 0, 0));
        UUID projectId = UUID.randomUUID();
        when(projectService.findProjectWorkOrders(projectId)).thenReturn(singleton("workorder"));

        bookingController.getMonthlyProjectBookings(projectId, null, null);

        verify(monthlyWorkOrderBookingService).fetchMonthlyWorkOrdersBookingView(singleton("workorder"), LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 1));
    }

    @Test
    void getMonthlyProjectBookings_boundaryDateInISOFormat() {
        UUID projectId = UUID.randomUUID();
        when(projectService.findProjectWorkOrders(projectId)).thenReturn(singleton("workorder"));

        bookingController.getMonthlyProjectBookings(projectId, "2018-01-01", "2018-01-05");

        verify(monthlyWorkOrderBookingService).fetchMonthlyWorkOrdersBookingView(singleton("workorder"), LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 1));
    }

    @Test
    void getMonthlyProjectBookings_invalidBoundaries_defaultsToCurrentMonth() {
        freezeTime(LocalDateTime.of(2018, 1, 1, 0, 0));
        UUID projectId = UUID.randomUUID();
        when(projectService.findProjectWorkOrders(projectId)).thenReturn(singleton("workorder"));

        bookingController.getMonthlyProjectBookings(projectId, "", "bla");

        verify(monthlyWorkOrderBookingService).fetchMonthlyWorkOrdersBookingView(singleton("workorder"), LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 1));
    }


    @Test
    void getYearlyProjectBookings() {
        UUID projectId = UUID.randomUUID();
        when(projectService.findProjectWorkOrders(projectId)).thenReturn(singleton("workorder"));

        bookingController.getYearlyProjectBookings(projectId, "2017", "2018");

        verify(yearlyWorkOrderBookingService).fetchYearlyWorkOrdersBookingView(singleton("workorder"), Year.of(2017), Year.of(2018));
    }

    @Test
    void getYearlyProjectBookings_yearMonth() {
        UUID projectId = UUID.randomUUID();
        when(projectService.findProjectWorkOrders(projectId)).thenReturn(singleton("workorder"));

        bookingController.getYearlyProjectBookings(projectId, "2017-01", "2018-01");

        verify(yearlyWorkOrderBookingService).fetchYearlyWorkOrdersBookingView(singleton("workorder"), Year.of(2017), Year.of(2018));
    }

    @Test
    void getYearlyProjectBookings_date() {
        UUID projectId = UUID.randomUUID();
        when(projectService.findProjectWorkOrders(projectId)).thenReturn(singleton("workorder"));

        bookingController.getYearlyProjectBookings(projectId, "2017-01-01", "2018-01-03");

        verify(yearlyWorkOrderBookingService).fetchYearlyWorkOrdersBookingView(singleton("workorder"), Year.of(2017), Year.of(2018));
    }

    @Test
    void getYearlyProjectBookings_noBoundaryDate_defaultsToCurrentYear() {
        freezeTime(LocalDateTime.of(2018, 1, 1, 0, 0));
        UUID projectId = UUID.randomUUID();
        when(projectService.findProjectWorkOrders(projectId)).thenReturn(singleton("workorder"));

        bookingController.getYearlyProjectBookings(projectId, null, null);

        verify(yearlyWorkOrderBookingService).fetchYearlyWorkOrdersBookingView(singleton("workorder"), Year.of(2018), Year.of(2018));
    }

    @Test
    void getYearlyProjectBookings_invalidBoundaries_defaultsToCurrentYear() {
        freezeTime(LocalDateTime.of(2018, 1, 1, 0, 0));
        UUID projectId = UUID.randomUUID();
        when(projectService.findProjectWorkOrders(projectId)).thenReturn(singleton("workorder"));

        bookingController.getYearlyProjectBookings(projectId, "", "bla");

        verify(yearlyWorkOrderBookingService).fetchYearlyWorkOrdersBookingView(singleton("workorder"), Year.of(2018), Year.of(2018));
    }
}
