package com.cegeka.project.booking;

import com.cegeka.project.workorder.WorkOrder;
import com.cegeka.project.workorder.WorkOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static com.cegeka.project.event.BookingCreatedTestBuilder.bookingCreated;
import static com.cegeka.project.event.BookingDeletedTestBuilder.bookingDeleted;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DayBookingReadModelTest {

    private DayBookingReadModel readModel;

    @Mock
    private DayBookingRepository dayBookingRepository;

    @Mock
    private WorkOrderRepository workOrderRepository;

    @BeforeEach
    void setUp() {
        readModel = new DayBookingReadModel(dayBookingRepository, workOrderRepository);
    }

    @Test
    void onBookingCreated_noBookingsAtDate_workOrderExists() {
        WorkOrder workOrder = new WorkOrder("workOrder");
        when(workOrderRepository.findByWorkOrder("workOrder")).thenReturn(Optional.of(workOrder));
        when(dayBookingRepository.findByDateAndWorkOrderWorkOrder(LocalDate.of(2018, 1, 1), "workOrder")).thenReturn(Optional.empty());
        BookingCreated event = bookingCreated()
                .date(LocalDate.of(2018, 1, 1))
                .workorder("workOrder")
                .hours(5D)
                .description("desc")
                .employee("1")
                .build();

        readModel.on(event);

        verify(dayBookingRepository).save(ArgumentMatchers.refEq(new DayBooking(LocalDate.of(2018, 1, 1), workOrder, 5D), "id"));
    }

    @Test
    void onBookingCreated_noBookingsAtDate_workOrderDoesNotExist() {
        when(workOrderRepository.findByWorkOrder("workOrder")).thenReturn(Optional.empty());
        when(dayBookingRepository.findByDateAndWorkOrderWorkOrder(LocalDate.of(2018, 1, 1), "workOrder")).thenReturn(Optional.empty());
        BookingCreated event = bookingCreated()
                .date(LocalDate.of(2018, 1, 1))
                .workorder("workOrder")
                .hours(5D)
                .description("desc")
                .employee("1")
                .build();

        assertThatThrownBy(() -> readModel.on(event)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void onBookingCreated_bookingsAtDate_workOrderExists() {
        WorkOrder workOrder = new WorkOrder("workOrder");
        DayBooking dayBooking = new DayBooking(LocalDate.of(2018, 1, 1), workOrder, 5D);
        when(dayBookingRepository.findByDateAndWorkOrderWorkOrder(LocalDate.of(2018, 1, 1), "workOrder")).thenReturn(Optional.of(dayBooking));
        BookingCreated event = bookingCreated()
                .date(LocalDate.of(2018, 1, 1))
                .workorder("workOrder")
                .hours(5D)
                .description("desc")
                .employee("1")
                .build();

        readModel.on(event);

        assertThat(dayBooking.getHours()).isEqualTo(10D);
    }

    @Test
    void onBookingDeleted_bookingsAtDate_subtractsBookingHours() {
        WorkOrder workOrder = new WorkOrder("workOrder");
        DayBooking dayBooking = new DayBooking(LocalDate.of(2018, 1, 1), workOrder, 5D);
        when(dayBookingRepository.findByDateAndWorkOrderWorkOrder(LocalDate.of(2018, 1, 1), "workOrder")).thenReturn(Optional.of(dayBooking));
        BookingDeleted event = bookingDeleted()
                .date(LocalDate.of(2018, 1, 1))
                .workorder("workOrder")
                .hours(2D)
                .description("desc")
                .employee("1")
                .build();

        readModel.on(event);

        assertThat(dayBooking.getHours()).isEqualTo(3D);
    }

    @Test
    void onBookingDeleted_noBookingsAtDate_doesNothing() {
        when(dayBookingRepository.findByDateAndWorkOrderWorkOrder(LocalDate.of(2018, 1, 1), "workOrder")).thenReturn(Optional.empty());
        BookingDeleted event = bookingDeleted()
                .date(LocalDate.of(2018, 1, 1))
                .workorder("workOrder")
                .hours(2D)
                .description("desc")
                .employee("1")
                .build();

        readModel.on(event);

        verifyNoMoreInteractions(dayBookingRepository);
    }

}