package com.cegeka.project.domain;

import com.cegeka.project.PersistenceTest;
import com.cegeka.project.booking.DayBooking;
import com.cegeka.project.booking.DayBookingRepository;
import com.cegeka.project.project.ProjectRepository;
import com.cegeka.project.workorder.WorkOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;

import static com.cegeka.project.domain.ProjectTestBuilder.project;
import static org.assertj.core.api.Assertions.assertThat;

class DayBookingRepositoryTest extends PersistenceTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private DayBookingRepository dayBookingRepository;

    @Test
    void findByDateAndWorkOrder() {
        WorkOrder workOrder = new WorkOrder("COCFL871.004");
        projectRepository.save(project()
                .workorder(workOrder)
                .build());
        dayBookingRepository.save(new DayBooking(LocalDate.of(2018, 1, 1), workOrder, 2));

        Optional<DayBooking> result = dayBookingRepository.findByDateAndWorkOrderWorkOrder(LocalDate.of(2018, 1, 1), "COCFL871.004");

        assertThat(result.get())
                .extracting(DayBooking::getWorkOrder, DayBooking::getDate, DayBooking::getHours)
                .containsExactly(workOrder, LocalDate.of(2018, 1, 1), 2D);
    }
}