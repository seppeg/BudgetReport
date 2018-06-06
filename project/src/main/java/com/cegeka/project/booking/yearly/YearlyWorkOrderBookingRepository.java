package com.cegeka.project.booking.yearly;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.Collection;
import java.util.List;

@Repository
@AllArgsConstructor
public class YearlyWorkOrderBookingRepository {

    private static final RowMapper<YearlyWorkOrderBookingView> ROW_MAPPER = (rs, rowNum) -> {
        String workOrder = rs.getString("work_order");
        int year = rs.getInt("year");
        double hours = rs.getDouble("hours");
        return new YearlyWorkOrderBookingView(workOrder, Year.of(year), hours);
    };

    private final NamedParameterJdbcTemplate jdbc;

    public List<YearlyWorkOrderBookingView> fetchYearlyWorkOrderBookingView(String workOrder, int startYear, int endYear) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("workOrder", workOrder)
                .addValue("startYear", startYear)
                .addValue("endYear", endYear);

        return jdbc.query("SELECT m.work_order, m.year, m.hours " +
                "FROM yearly_work_order_bookings m " +
                "WHERE m.work_order = :workOrder " +
                "AND m.year >= :startYear " +
                "AND m.year <= :endYear " +
                "ORDER BY m.work_order, m.year", params, ROW_MAPPER);
    }

    public List<YearlyWorkOrderBookingView> fetchYearlyWorkOrdersBookingView(Collection<String> workOrders, int startYear, int endYear) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("workOrders", workOrders)
                .addValue("startYear", startYear)
                .addValue("endYear", endYear);

        return jdbc.query("SELECT m.work_order, m.year, m.hours " +
                "FROM yearly_work_order_bookings m " +
                "WHERE m.work_order in (:workOrders) " +
                "AND m.year >= :startYear " +
                "AND m.year <= :endYear " +
                "ORDER BY m.work_order, m.year", params, ROW_MAPPER);
    }

    public void refreshView(){
        jdbc.update("REFRESH MATERIALIZED VIEW yearly_work_order_bookings", EmptySqlParameterSource.INSTANCE);
    }

}
