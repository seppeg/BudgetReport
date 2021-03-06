package com.cegeka.project.workorder;

import com.cegeka.project.infrastructure.ZookeeperFacade;
import com.cegeka.project.project.ProjectCreated;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
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

    @EventListener
    public void onProjectCreated(ProjectCreated event){
        trackWorkOrders(event.getWorkOrders());
    }

    public void trackWorkOrders(Collection<String> workOrders) {
        if (this.zookeeperFacade.zNodeExists(WORKORDER_CONFIG_ZNODE)) {
            addWorkOrdersToAlreadyTrackedWorkOrders(WORKORDER_CONFIG_ZNODE, workOrders);
        } else {
            zookeeperFacade.createZNode(WORKORDER_CONFIG_ZNODE, serializeWorkOrders(workOrders));
        }
    }

    private void addWorkOrdersToAlreadyTrackedWorkOrders(String path, Collection<String> workOrders) {
        Set<String> currentlyTrackedWorkOrders = fetchTrackedWorkOrders();
        currentlyTrackedWorkOrders.addAll(workOrders);
        String newTrackedWorkOrders = serializeWorkOrders(currentlyTrackedWorkOrders);
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

    private String serializeWorkOrders(Collection<String> workOrders) {
        return workOrders.stream().collect(joining(","));
    }

}
