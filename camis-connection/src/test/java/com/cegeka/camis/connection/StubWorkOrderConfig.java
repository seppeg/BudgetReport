package com.cegeka.camis.connection;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class StubWorkOrderConfig extends WorkOrderConfig {
    private final List<String> workOrders;

    public StubWorkOrderConfig(String... workOrders) {
        this.workOrders = newArrayList(workOrders);
    }

    @Override
    public List<String> getTrackedWorkOrders() {
        return workOrders;
    }
}
