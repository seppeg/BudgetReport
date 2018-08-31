package com.cegeka.project.project.spec;

import lombok.Value;

@Value
public class WorkOrderMatchingRuleR implements MatchingRuleR {
    private final String workOrder;

    public WorkOrderMatchingRuleR(String workOrder) {
        this.workOrder = workOrder;
    }
}
