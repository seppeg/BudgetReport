package com.cegeka.project.booking.monthly;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;
import java.util.List;

@Repository
@AllArgsConstructor
public class MonthlyWorkOrderBookingRepository {

    private static final RowMapper<MonthlyWorkOrderBookingView> ROW_MAPPER = (rs, rowNum) -> {
        String workOrder = rs.getString("work_order");
        LocalDate yearMonth = rs.getDate("year_month").toLocalDate();
        double hours = rs.getDouble("hours");
        return new MonthlyWorkOrderBookingView(workOrder, YearMonth.from(yearMonth), hours);
    };

    private final NamedParameterJdbcTemplate jdbc;

    public List<MonthlyWorkOrderBookingView> fetchMonthlyWorkOrderBookingView(String workOrder, LocalDate startDate, LocalDate endDate) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("workOrder", workOrder)
                .addValue("startDate", startDate)
                .addValue("endDate", endDate);

        return jdbc.query("SELECT m.work_order, m.year_month, m.hours " +
                "FROM monthly_work_order_bookings m " +
                "WHERE m.work_order = :workOrder " +
                "AND m.year_month >= :startDate " +
                "AND m.year_month <= :endDate " +
                "ORDER BY m.work_order, m.year_month", params, ROW_MAPPER);
    }

    public List<MonthlyWorkOrderBookingView> fetchMonthlyWorkOrdersBookingView(Collection<String> workOrders, LocalDate startDate, LocalDate endDate) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("workOrders", workOrders)
                .addValue("startDate", startDate)
                .addValue("endDate", endDate);

        return jdbc.query("SELECT m.work_order, m.year_month, m.hours " +
                "FROM monthly_work_order_bookings m " +
                "WHERE m.work_order in (:workOrders) " +
                "AND m.year_month >= :startDate " +
                "AND m.year_month <= :endDate " +
                "ORDER BY m.work_order, m.year_month", params, ROW_MAPPER);
    }

    public void refreshView(){
        jdbc.update("REFRESH MATERIALIZED VIEW monthly_work_order_bookings", EmptySqlParameterSource.INSTANCE);
    }

}
