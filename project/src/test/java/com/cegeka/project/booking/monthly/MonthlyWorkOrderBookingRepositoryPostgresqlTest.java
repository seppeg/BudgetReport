package com.cegeka.project.booking.monthly;

import com.cegeka.project.PersistenceTest;
import com.cegeka.project.booking.DayBooking;
import com.cegeka.project.booking.DayBookingRepository;
import com.cegeka.project.project.Project;
import com.cegeka.project.project.ProjectRepository;
import com.cegeka.project.project.ProjectYearBudget;
import com.cegeka.project.workorder.WorkOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class MonthlyWorkOrderBookingRepositoryPostgresqlTest extends PersistenceTest {

    @Autowired
    private MonthlyWorkOrderBookingRepository monthlyWorkOrderBookingRepository;

    @Autowired
    private DayBookingRepository dayBookingRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void fetchMonthlyWorkOrderBookingView() {
        WorkOrder workOrder = new WorkOrder("COCFL871.004");
        projectRepository.save(new Project("test", singleton(workOrder), singleton(new ProjectYearBudget(2018, 100D))));
        dayBookingRepository.save(new DayBooking(LocalDate.of(2018, 2, 1), workOrder, 10D));
        flush();
        monthlyWorkOrderBookingRepository.refreshView();

        List<MonthlyWorkOrderBookingView> result = monthlyWorkOrderBookingRepository.fetchMonthlyWorkOrderBookingView("COCFL871.004", LocalDate.of(2018, 1, 1), LocalDate.now());

        assertThat(result)
                .extracting(MonthlyWorkOrderBookingView::getWorkOrder, MonthlyWorkOrderBookingView::getYearMonth, MonthlyWorkOrderBookingView::getHours)
                .containsExactly(tuple("COCFL871.004", YearMonth.of(2018, 2), 10D));
    }

}