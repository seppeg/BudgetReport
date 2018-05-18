package com.cegeka.camis.booking;

import com.cegeka.camis.connection.CamisRepository;
import com.cegeka.timeprovider.Timeprovider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

@Component
public class BookingService {
    private CamisRepository camisRepository;
    private BookingConnection bookingConnection;

    @Autowired
    BookingService(CamisRepository camisRepository, BookingConnection bookingConnection){
        this.camisRepository = camisRepository;
        this.bookingConnection = bookingConnection;
    }

    public void updateBookings() {
        bookingConnection.sendDataToBookingDomain(camisRepository.findActualsFor(createPeriod()));
    }

    private int createPeriod() {
        LocalDate now = Timeprovider.getNow();
        now = now.minusMonths(3);
        int weekNumber = getWeekNumber(now);
        return (now.getYear() * 100) +  weekNumber;
    }

    private int getWeekNumber(LocalDate now) {
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        return now.get(woy);
    }
}
