package com.cegeka.camis.booking;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

public class CamisBooking {

    private String workorder;
    private LocalDate date;
    private String description;
    private double hours;
    private String employee;

    public String getWorkorder() {
        return workorder;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getHours() {
        return hours;
    }

    public String getEmployee() {
        return employee;
    }

    @Override
    public String toString() {
        return "CamisBooking{" +
                "workorder='" + workorder + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", hours=" + hours +
                ", employee='" + employee + '\'' +
                '}';
    }

    public static class Builder {

        private final CamisBooking camisBooking;

        private Builder(CamisBooking camisBooking) {
            this.camisBooking = camisBooking;
        }

        public static Builder booking() {
            return new Builder(new CamisBooking());
        }

        public CamisBooking build() {
            return camisBooking;
        }

        public Builder workorder(String werkorder) {
            camisBooking.workorder = werkorder;
            return this;
        }

        public Builder date(Date date) {
            camisBooking.date = date.toLocalDate();
            return this;
        }

        public Builder description(String description) {
            camisBooking.description = description;
            return this;
        }

        public Builder hours(BigDecimal hours) {
            camisBooking.hours = hours.doubleValue();
            return this;
        }

        public Builder employee(String employee) {
            camisBooking.employee = employee;
            return this;
        }
    }
}
