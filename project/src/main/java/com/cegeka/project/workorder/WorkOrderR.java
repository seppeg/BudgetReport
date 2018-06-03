package com.cegeka.project.workorder;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class WorkOrderR {
    private final String workOrder;

    public WorkOrderR(WorkOrder workOrder){
        this.workOrder = workOrder.getWorkOrder();
    }
}
