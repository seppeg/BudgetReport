package com.cegeka.project.service;

import com.cegeka.project.domain.daybooking.MonthlyWorkOrderBookingRepository;
import com.cegeka.project.domain.daybooking.MonthlyWorkOrderBookingView;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
@AllArgsConstructor
@Transactional
public class ProjectBookingService {

    private final MonthlyWorkOrderBookingRepository monthlyWorkOrderBookingRepository;

    public Collection<MonthlyWorkOrderBookingView> getMonthlyWorkOrderBookings(int year){
        return monthlyWorkOrderBookingRepository.findAllByYearOrderByWorkOrderAndYearMonth(year);
    }

    public Collection<MonthlyWorkOrderBookingView> getMonthlyWorkOrderBookings(String workOrder){
        return monthlyWorkOrderBookingRepository.findAllByWorkOrderOrderByYearMonth(workOrder);
    }

    public Collection<MonthlyWorkOrderBookingView> getMonthlyWorkOrderBookings(String workOrder, int year){
        return monthlyWorkOrderBookingRepository.findAllByWorkOrderAndYearOrderByYearMonth(workOrder, year);
    }

    public Optional<MonthlyWorkOrderBookingView> getMonthlyWorkOrderBooking(String workOrder, YearMonth yearMonth){
        return monthlyWorkOrderBookingRepository.findByWorkOrderAndYearMonth(workOrder, yearMonth);
    }

    public Collection<MonthlyWorkOrderBookingView> getMonthlyWorkOrderBookings(Collection<String> workOrders){
        if(workOrders.isEmpty()){
            return emptyList();
        }
        return monthlyWorkOrderBookingRepository.findAllByWorkOrderInOrderByYearMonth(workOrders);
    }
}
