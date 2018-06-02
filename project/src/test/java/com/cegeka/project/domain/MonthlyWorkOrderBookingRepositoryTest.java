package com.cegeka.project.domain;

import com.cegeka.project.domain.daybooking.MonthlyWorkOrderBookingRepository;
import com.cegeka.project.domain.daybooking.MonthlyWorkOrderBookingView;
import com.cegeka.project.infrastructure.ZookeeperFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.cloud.zookeeper.enabled=false")
@ExtendWith(SpringExtension.class)
@Transactional
class MonthlyWorkOrderBookingRepositoryTest {

    @Autowired
    private MonthlyWorkOrderBookingRepository monthlyWorkOrderBookingRepository;

    @MockBean
    private ZookeeperFacade zookeeperFacade;

    @Test
    void findAllByWorkOrder() {
        monthlyWorkOrderBookingRepository.save(new MonthlyWorkOrderBookingView("a", YearMonth.of(2018, 1), 5));
        monthlyWorkOrderBookingRepository.save(new MonthlyWorkOrderBookingView("b", YearMonth.of(2018, 2), 7));

        Collection<MonthlyWorkOrderBookingView> result = monthlyWorkOrderBookingRepository.findAllByWorkOrderOrderByYearMonth("a");

        assertThat(result).hasSize(1);
        MonthlyWorkOrderBookingView view = result.iterator().next();
        assertThat(view.getWorkOrder()).isEqualTo("a");
        assertThat(view.getHours()).isEqualTo(5D);
    }

    @Test
    void findAllByWorkOrderAndYearMonth() {
        monthlyWorkOrderBookingRepository.save(new MonthlyWorkOrderBookingView("a", YearMonth.of(2018, 1), 5));
        monthlyWorkOrderBookingRepository.save(new MonthlyWorkOrderBookingView("a", YearMonth.of(2018, 2), 7));

        Optional<MonthlyWorkOrderBookingView> result = monthlyWorkOrderBookingRepository.findByWorkOrderAndYearMonth("a", YearMonth.of(2018, 1));

        MonthlyWorkOrderBookingView view = result.get();
        assertThat(view.getWorkOrder()).isEqualTo("a");
        assertThat(view.getHours()).isEqualTo(5D);
    }

    @Test
    void findAllByWorkOrderAndYearMonthYear() {
        monthlyWorkOrderBookingRepository.save(new MonthlyWorkOrderBookingView("a", YearMonth.of(2018, 1), 5));
        monthlyWorkOrderBookingRepository.save(new MonthlyWorkOrderBookingView("a", YearMonth.of(2019, 1), 7));

        Collection<MonthlyWorkOrderBookingView> result = monthlyWorkOrderBookingRepository.findAllByWorkOrderAndYearOrderByYearMonth("a", 2018);

        assertThat(result).hasSize(1);
        MonthlyWorkOrderBookingView view = result.iterator().next();
        assertThat(view.getWorkOrder()).isEqualTo("a");
        assertThat(view.getHours()).isEqualTo(5D);
    }

    @Test
    void findAllByYear() {
        monthlyWorkOrderBookingRepository.save(new MonthlyWorkOrderBookingView("a", YearMonth.of(2018, 1), 5));
        monthlyWorkOrderBookingRepository.save(new MonthlyWorkOrderBookingView("b", YearMonth.of(2019, 1), 7));

        Collection<MonthlyWorkOrderBookingView> result = monthlyWorkOrderBookingRepository.findAllByYearOrderByWorkOrderAndYearMonth(2018);

        assertThat(result).hasSize(1);
        MonthlyWorkOrderBookingView view = result.iterator().next();
        assertThat(view.getWorkOrder()).isEqualTo("a");
        assertThat(view.getHours()).isEqualTo(5D);
    }

}