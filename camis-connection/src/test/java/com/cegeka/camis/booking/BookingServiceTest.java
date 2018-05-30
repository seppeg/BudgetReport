package com.cegeka.camis.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.cegeka.camis.booking.CamisBooking.Builder.booking;
import static com.cegeka.timeprovider.Timeprovider.freezeTime;
import static java.time.LocalDateTime.of;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private CamisRepository camisRepository;
    @Mock
    private BookingConnection bookingConnection;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void updateBookings() {
        List<CamisBooking> expected = asList(booking().build(), booking().build());
        freezeTime(of(2018, 4,2,10,10,10));
        when(camisRepository.findActualsFor(201801)).thenReturn(expected);

        bookingService.updateBookings();

        verify(bookingConnection).sendDataToBookingDomain(expected);
    }
}