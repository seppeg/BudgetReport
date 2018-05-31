package com.cegeka.camis.connection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

@Component
@RefreshScope
public class WorkOrderConfig {

    @Value("${workorders:}")
    private String trackedWorkOrders;

    public List<String> getTrackedWorkOrders() {
        String[] workOrders = trackedWorkOrders.split(",");
        return workOrders.length == 1 && workOrders[0].isEmpty()
                ? emptyList()
                : asList(workOrders);
    }

}
