package com.cegeka.project.booking.yearly;

import com.cegeka.project.booking.BookingCreated;
import com.cegeka.project.booking.BookingDeleted;
import com.cegeka.project.infrastructure.debounce.Debounce;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.Year;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.emptyList;

@Service
@Log4j2
@Transactional
public class YearlyWorkOrderBookingService {

    private final YearlyWorkOrderBookingRepository bookingRepository;

    public YearlyWorkOrderBookingService(YearlyWorkOrderBookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<YearlyWorkOrderBookingView> fetchYearlyWorkOrderBookingView(String workOrder, Year startYear, Year endYear){
        return bookingRepository.fetchYearlyWorkOrderBookingView(workOrder, startYear.getValue(), endYear.getValue());
    }

    public List<YearlyWorkOrderBookingView> fetchYearlyWorkOrdersBookingView(Collection<String> workOrders, Year startYear, Year endYear){
        if(workOrders.isEmpty()){
            return emptyList();
        }
        return bookingRepository.fetchYearlyWorkOrdersBookingView(workOrders, startYear.getValue(), endYear.getValue());
    }

    @Debounce
    @TransactionalEventListener
    public void on(BookingCreated event){
        bookingRepository.refreshView();
    }

    @Debounce
    @TransactionalEventListener
    public void on(BookingDeleted event){
        bookingRepository.refreshView();
    }

}
