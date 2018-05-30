package com.cegeka.project.service;

import lombok.AllArgsConstructor;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static io.netty.util.CharsetUtil.UTF_8;
import static java.util.stream.Collectors.joining;

@AllArgsConstructor
@Component
public class WorkOrderTracker {

    private static final String WORKORDER_CONFIG_ZNODE = "/config/camisconnection/workorders";

    private final CuratorFramework curatorFramework;

    public void trackWorkOrders(Collection<String> workOrders) {
        try {
            if (!zNodeExists()) {
                createZNode(WORKORDER_CONFIG_ZNODE);
            }
            addWorkOrdersToAlreadyTrackedWorkOrders(WORKORDER_CONFIG_ZNODE, workOrders);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addWorkOrdersToAlreadyTrackedWorkOrders(String path, Collection<String> workOrders) throws Exception {
        Set<String> currentlyTrackedWorkOrders = fetchTrackedWorkOrders();
        currentlyTrackedWorkOrders.addAll(workOrders);
        String newTrackedWorkOrders = currentlyTrackedWorkOrders.stream().collect(joining(","));
        this.curatorFramework.setData().forPath(path, newTrackedWorkOrders.getBytes(UTF_8));
    }

    private void createZNode(String path) throws Exception {
        CuratorOp op = this.curatorFramework.transactionOp().create()
                .withMode(CreateMode.PERSISTENT)
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                .forPath(path, "".getBytes(UTF_8));
        curatorFramework.transaction().forOperations(op);
    }

    private Set<String> fetchTrackedWorkOrders() throws Exception {
        String currentlyTrackedWorkOrders = new String(this.curatorFramework.getData().forPath(WORKORDER_CONFIG_ZNODE), UTF_8);
        return newHashSet(currentlyTrackedWorkOrders.split(","));
    }

    private boolean zNodeExists() {
        try {
            return this.curatorFramework.checkExists()
                    .creatingParentsIfNeeded()
                    .forPath(WORKORDER_CONFIG_ZNODE) != null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
