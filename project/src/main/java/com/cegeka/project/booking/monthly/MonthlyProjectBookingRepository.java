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
public class MonthlyProjectBookingRepository {

    private static final RowMapper<MonthlyProjectBookingView> ROW_MAPPER = (rs, rowNum) -> {
        String project = rs.getString("project");
        LocalDate yearMonth = rs.getDate("year_month").toLocalDate();
        double hours = rs.getDouble("hours");
        return new MonthlyProjectBookingView(project, YearMonth.from(yearMonth), hours);
    };

    private final NamedParameterJdbcTemplate jdbc;

    public List<MonthlyProjectBookingView> fetchMonthlyProjectBookingView(String project, LocalDate startDate, LocalDate endDate) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("project", project)
                .addValue("startDate", startDate)
                .addValue("endDate", endDate);

        return jdbc.query("SELECT m.project, m.year_month, m.hours " +
                "FROM monthly_project_bookings m " +
                "WHERE m.project = :project " +
                "AND m.year_month >= :startDate " +
                "AND m.year_month <= :endDate " +
                "ORDER BY m.project, m.year_month", params, ROW_MAPPER);
    }

    public void refreshView(){
        jdbc.update("REFRESH MATERIALIZED VIEW monthly_project_bookings", EmptySqlParameterSource.INSTANCE);
    }

}
