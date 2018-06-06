package com.cegeka.project.booking;

import com.cegeka.project.booking.monthly.MonthlyWorkOrderBookingService;
import com.cegeka.project.booking.monthly.MonthlyWorkOrderBookingView;
import com.cegeka.project.booking.yearly.YearlyWorkOrderBookingService;
import com.cegeka.project.booking.yearly.YearlyWorkOrderBookingView;
import com.cegeka.project.project.ProjectService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.Collection;
import java.util.UUID;

import static com.cegeka.timeprovider.Timeprovider.getCurrentYear;
import static com.cegeka.timeprovider.Timeprovider.getCurrentYearMonth;

@RestController
@AllArgsConstructor
@Log4j2
public class BookingController {

    private final ProjectService projectService;
    private final MonthlyWorkOrderBookingService monthlyWorkOrderBookingService;
    private final YearlyWorkOrderBookingService yearlyWorkOrderBookingService;

    @GetMapping("/project/{projectId}/booking/monthly")
    public Collection<MonthlyWorkOrderBookingView> getMonthlyProjectBookings(@PathVariable("projectId") UUID projectId,
                                                                             @RequestParam(value = "start", required = false) String start,
                                                                             @RequestParam(value = "end", required = false) String end){
        Collection<String> projectWorkOrders = projectService.findProjectWorkOrders(projectId);
        return monthlyWorkOrderBookingService.fetchMonthlyWorkOrdersBookingView(projectWorkOrders, parseYearMonth(start).atDay(1), parseYearMonth(end).atDay(1));
    }

    @GetMapping("/project/{projectId}/booking/yearly")
    public Collection<YearlyWorkOrderBookingView> getYearlyProjectBookings(@PathVariable("projectId") UUID projectId,
                                                                            @RequestParam(value = "start", required = false) String start,
                                                                            @RequestParam(value = "end", required = false) String end){
        Collection<String> projectWorkOrders = projectService.findProjectWorkOrders(projectId);
        return yearlyWorkOrderBookingService.fetchYearlyWorkOrdersBookingView(projectWorkOrders, parseYear(start), parseYear(end));
    }

    private YearMonth parseYearMonth(String yearMonthString){
        if(yearMonthString == null){
            return getCurrentYearMonth();
        }
        try {
            return YearMonth.parse(yearMonthString);
        } catch (Exception e) {
            try {
                return YearMonth.from(LocalDate.parse(yearMonthString));
            } catch (Exception e1) {
                return getCurrentYearMonth();
            }
        }
    }

    private Year parseYear(String yearString) {
        if(yearString == null){
            return getCurrentYear();
        }
        try {
            return Year.parse(yearString);
        } catch (Exception ignore) {
            try {
                return Year.of(YearMonth.parse(yearString).getYear());
            } catch (Exception ignore2) {
                try {
                    return Year.of(LocalDate.parse(yearString).getYear());
                } catch (Exception ignore3) {
                    return getCurrentYear();
                }
            }
        }
    }
}
