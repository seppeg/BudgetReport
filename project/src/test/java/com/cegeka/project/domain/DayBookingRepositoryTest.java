package com.cegeka.project.domain;

import com.cegeka.project.booking.DayBooking;
import com.cegeka.project.booking.DayBookingRepository;
import com.cegeka.project.infrastructure.ZookeeperFacade;
import com.cegeka.project.project.ProjectRepository;
import com.cegeka.project.workorder.WorkOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

import static com.cegeka.project.domain.ProjectTestBuilder.project;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(properties = "spring.cloud.zookeeper.enabled=false")
@ExtendWith(SpringExtension.class)
@Transactional
class DayBookingRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private DayBookingRepository dayBookingRepository;

    @MockBean
    private ZookeeperFacade zookeeperFacade;

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