package com.cegeka.project.project.spec;

import com.cegeka.project.booking.BookingEvent;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
@Getter
@ToString
public class WorkOrderMatchingRule extends MatchingRule {

    private String workOrder;

    WorkOrderMatchingRule(){}

    public WorkOrderMatchingRule(String workOrder){
        super(UUID.randomUUID());
        this.workOrder = workOrder;
    }

    @Override
    public boolean matches(BookingEvent bookingEvent) {
        return workOrder.equals(bookingEvent.getWorkOrder());
    }
}
