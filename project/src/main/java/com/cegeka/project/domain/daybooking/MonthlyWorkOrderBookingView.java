package com.cegeka.project.domain.daybooking;

import com.cegeka.project.util.YearMonthType;
import lombok.*;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.time.YearMonth;

@Entity
@IdClass(MonthlyWorkOrderBookingView.MonthlyWorkOrderBookingKey.class)
@Table(name = "monthly_work_order_bookings")
@Immutable
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@TypeDef(name = "yearMonth", typeClass = YearMonthType.class)
public class MonthlyWorkOrderBookingView {

    @Id
    private String workOrder;

    @Id
    @Type(type = "com.cegeka.project.util.YearMonthType")
    @Columns(columns = {
            @Column(name = "year"),
            @Column(name = "month")
    })
    private YearMonth yearMonth;

    private double hours;

    static class MonthlyWorkOrderBookingKey implements Serializable {
        private String workOrder;
        private YearMonth yearMonth;
    }
}
