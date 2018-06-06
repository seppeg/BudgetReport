package com.cegeka.project.booking.monthly;

import com.cegeka.project.infrastructure.ZookeeperFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest(properties = "spring.cloud.zookeeper.enabled=false")
@ExtendWith(SpringExtension.class)
@Transactional
class MonthlyWorkOrderBookingRepositoryTest {

    @Autowired
    private MonthlyWorkOrderBookingRepository monthlyWorkOrderBookingRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @MockBean
    private ZookeeperFacade zookeeperFacade;

    @Autowired
    private Collection<ConversionService> conv;

    @Test
    void fetchMonthlyWorkOrderBookingView() {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("workOrder", "COCFL871.004")
                .addValue("yearMonth", LocalDate.of(2018, 2, 1))
                .addValue("hours", 10);
        jdbcTemplate.update("INSERT INTO monthly_work_order_bookings (work_order, year_month, hours) VALUES (:workOrder, :yearMonth, :hours)", params);

        List<MonthlyWorkOrderBookingView> result = monthlyWorkOrderBookingRepository.fetchMonthlyWorkOrderBookingView("COCFL871.004", LocalDate.of(2018, 1, 1), LocalDate.now());

        assertThat(result)
                .extracting(MonthlyWorkOrderBookingView::getWorkOrder, MonthlyWorkOrderBookingView::getYearMonth, MonthlyWorkOrderBookingView::getHours)
                .containsExactly(tuple("COCFL871.004", YearMonth.of(2018, 2), 10D));
    }
}