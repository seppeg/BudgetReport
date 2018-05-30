package com.cegeka.camis.connection;

import com.cegeka.camis.booking.CamisBooking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.cegeka.camis.booking.CamisBooking.Builder.booking;

@Repository
public class CamisRepository {

    private JdbcTemplate jdbcTemplate;
    private String workorders;

    @Autowired
    public CamisRepository(DataSource ds, @Value("#{'${workorders}'.split(',')}")
            List<String> workorders) {
        jdbcTemplate = new JdbcTemplate(ds);
        this.workorders = workorders.stream()
                .map(workorder -> "'" + workorder + "'")
                .collect(Collectors.joining(","));
    }

    public List<CamisBooking> findActualsFor(int period) {
        String sql = "SELECT Werkorder, Datum, Commentaar, Uren, Medewerker_ID " +
                "FROM dbo.zrapuur1 " +
                "WHERE Periode >=" + period + " "+
                "AND Werkorder in ( " +
                getWorkorders()
                + " )";

        return jdbcTemplate.query(sql, new MapToCamisBooking());
    }

    private String getWorkorders() {
        return workorders;
    }

    private static class MapToCamisBooking implements ResultSetExtractor<List<CamisBooking>> {
        @Override
        public List<CamisBooking> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<CamisBooking> camisBookings = new ArrayList<>();

            while (rs.next()) {
                camisBookings.add(rsItemToCamisBooking(rs));
            }
            return camisBookings;
        }

        private CamisBooking rsItemToCamisBooking(ResultSet rs) throws SQLException {
            return booking()
                    .workorder(rs.getString("Werkorder"))
                    .date(rs.getDate("Datum"))
                    .description(rs.getString("Commentaar"))
                    .hours(rs.getBigDecimal("Uren"))
                    .employee(rs.getString("Medewerker_ID"))
                    .build();
        }
    }
}
