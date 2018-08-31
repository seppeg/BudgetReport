package com.cegeka.project.booking.monthly;

import com.cegeka.project.PersistenceTest;
import com.cegeka.project.booking.DayBooking;
import com.cegeka.project.booking.DayBookingRepository;
import com.cegeka.project.project.Project;
import com.cegeka.project.project.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class MonthlyProjectBookingRepositoryPostgresqlTest extends PersistenceTest {

    @Autowired
    private MonthlyProjectBookingRepository monthlyProjectBookingRepository;

    @Autowired
    private DayBookingRepository dayBookingRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void fetchMonthlyWorkOrderBookingView() {
        Project project = projectRepository.save(new Project("test", 100));
        dayBookingRepository.save(new DayBooking(LocalDate.of(2018, 2, 1), project.getId(), 10D));
        flush();
        monthlyProjectBookingRepository.refreshView();

        List<MonthlyProjectBookingView> result = monthlyProjectBookingRepository.fetchMonthlyProjectBookingView("test", LocalDate.of(2018, 1, 1), LocalDate.now());

        assertThat(result)
                .extracting(MonthlyProjectBookingView::getProject, MonthlyProjectBookingView::getYearMonth, MonthlyProjectBookingView::getHours)
                .containsExactly(tuple("test", YearMonth.of(2018, 2), 10D));
    }

}