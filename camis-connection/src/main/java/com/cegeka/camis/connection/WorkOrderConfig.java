package com.cegeka.camis.connection;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static io.netty.util.CharsetUtil.UTF_8;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

@Component
@RefreshScope
public class WorkOrderConfig {

    private static final String WORKORDER_CONFIG_ZKNODE = "/config/camisconnection/workorders";

    private final CuratorFramework curatorFramework;

    @Value("${workorders:}")
    private String trackedWorkOrders;

    public WorkOrderConfig(CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
    }

    public List<String> getTrackedWorkOrders() {
        return asList(trackedWorkOrders.split(","));
    }

    public void trackWorkOrders(Collection<String> workOrders) {
        try {
            if (!zkNodeExists()) {
                CuratorOp op = this.curatorFramework.transactionOp().create()
                        .withMode(CreateMode.PERSISTENT)
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                        .forPath(WORKORDER_CONFIG_ZKNODE, "".getBytes(UTF_8));
                curatorFramework.transaction().forOperations(op);
            }
            String addedWorkOrders = workOrders.stream().collect(joining(","));
            String newTrackedWorkOrders = trackedWorkOrders.isEmpty()
                    ? addedWorkOrders
                    : (trackedWorkOrders + "," + addedWorkOrders);
            this.curatorFramework.setData().forPath(WORKORDER_CONFIG_ZKNODE, newTrackedWorkOrders.getBytes(UTF_8));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean zkNodeExists() {
        try {
            return this.curatorFramework.checkExists()
                    .creatingParentsIfNeeded()
                    .forPath(WORKORDER_CONFIG_ZKNODE) != null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
