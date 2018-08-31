package com.cegeka.project.booking;

import com.cegeka.project.booking.monthly.MonthlyProjectBookingService;
import com.cegeka.project.booking.monthly.MonthlyProjectBookingView;
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
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static com.cegeka.timeprovider.Timeprovider.getCurrentYear;
import static com.cegeka.timeprovider.Timeprovider.getCurrentYearMonth;
import static java.util.Collections.emptyList;

@RestController
@AllArgsConstructor
@Log4j2
public class BookingController {

    private final ProjectService projectService;
    private final MonthlyProjectBookingService monthlyProjectBookingService;

    @GetMapping("/project/{projectId}/booking/monthly")
    public Collection<MonthlyProjectBookingView> getMonthlyProjectBookings(@PathVariable("projectId") UUID projectId,
                                                                           @RequestParam(value = "start", required = false) String start,
                                                                           @RequestParam(value = "end", required = false) String end) {
        return projectService.getProjectName(projectId)
                .map(project -> monthlyProjectBookingService.fetchMonthlyProjectBookingView(project, parseYearMonth(start).atDay(1), parseYearMonth(end).atDay(1)))
                .orElse(emptyList());
    }

    private YearMonth parseYearMonth(String yearMonthString) {
        if (yearMonthString == null) {
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
}
