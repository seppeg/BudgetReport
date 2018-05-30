package com.cegeka.camis.connection;

import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class StubWorkOrderConfig extends WorkOrderConfig {
    private final List<String> workOrders;

    public StubWorkOrderConfig(String... workOrders) {
        super(null);
        this.workOrders = newArrayList(workOrders);
    }

    @Override
    public void trackWorkOrders(Collection<String> workOrders) {
        this.workOrders.addAll(workOrders);
    }

    @Override
    public List<String> getTrackedWorkOrders() {
        return workOrders;
    }
}
