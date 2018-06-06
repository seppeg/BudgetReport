package com.cegeka.project.booking.monthly;

import com.cegeka.project.booking.BookingCreated;
import com.cegeka.project.booking.BookingDeleted;
import com.cegeka.project.infrastructure.debounce.Debounce;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.emptyList;

@Service
@Log4j2
@Transactional
@AllArgsConstructor
public class MonthlyWorkOrderBookingService {

    private final MonthlyWorkOrderBookingRepository bookingRepository;

    public List<MonthlyWorkOrderBookingView> fetchMonthlyWorkOrderBookingView(String workOrder, LocalDate startDate, LocalDate endDate){
        return bookingRepository.fetchMonthlyWorkOrderBookingView(workOrder, startDate, endDate);
    }

    public List<MonthlyWorkOrderBookingView> fetchMonthlyWorkOrdersBookingView(Collection<String> workOrders, LocalDate startDate, LocalDate endDate){
        if(workOrders.isEmpty()){
            return emptyList();
        }
        return bookingRepository.fetchMonthlyWorkOrdersBookingView(workOrders, startDate, endDate);
    }

    @TransactionalEventListener
    @Debounce
    public void on(BookingCreated event){
        bookingRepository.refreshView();
    }

    @Debounce
    @TransactionalEventListener
    public void on(BookingDeleted event){
        bookingRepository.refreshView();
    }

}
