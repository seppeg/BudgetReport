package com.cegeka.camis.connection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Arrays.asList;

@Component
@RefreshScope
public class WorkOrderConfig {

    @Value("${workorders:}")
    private String trackedWorkOrders;

    public List<String> getTrackedWorkOrders() {
        return asList(trackedWorkOrders.split(","));
    }

}
