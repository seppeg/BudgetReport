package com.cegeka.project.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.joining;

@AllArgsConstructor
@Component
public class WorkOrderTracker {

    private static final String WORKORDER_CONFIG_ZNODE = "/config/camisconnection/workorders";

    private final ZookeeperFacade zookeeperFacade;

    public void trackWorkOrders(Collection<String> workOrders) {
        if (this.zookeeperFacade.zNodeExists(WORKORDER_CONFIG_ZNODE)) {
            addWorkOrdersToAlreadyTrackedWorkOrders(WORKORDER_CONFIG_ZNODE, workOrders);
        } else {
            zookeeperFacade.createZNode(WORKORDER_CONFIG_ZNODE, toString(workOrders));
        }
    }

    private void addWorkOrdersToAlreadyTrackedWorkOrders(String path, Collection<String> workOrders) {
        Set<String> currentlyTrackedWorkOrders = fetchTrackedWorkOrders();
        currentlyTrackedWorkOrders.addAll(workOrders);
        String newTrackedWorkOrders = toString(currentlyTrackedWorkOrders);
        if (!newTrackedWorkOrders.isEmpty()) {
            this.zookeeperFacade.setZNodeValue(path, newTrackedWorkOrders);
        }
    }

    private Set<String> fetchTrackedWorkOrders() {
        String currentlyTrackedWorkOrders = this.zookeeperFacade.getZNodeValueAsString(WORKORDER_CONFIG_ZNODE);
        String[] splittedOrders = currentlyTrackedWorkOrders.split(",");
        return splittedOrders.length == 1 && splittedOrders[0].isEmpty()
                ? emptySet()
                : newHashSet(splittedOrders);
    }

    private String toString(Collection<String> workOrders) {
        return workOrders.stream().collect(joining(","));
    }

}
