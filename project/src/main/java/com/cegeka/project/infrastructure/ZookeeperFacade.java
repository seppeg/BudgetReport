package com.cegeka.project.infrastructure;

import lombok.AllArgsConstructor;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.springframework.stereotype.Service;

import static io.netty.util.CharsetUtil.UTF_8;

@Service
@AllArgsConstructor
public class ZookeeperFacade {

    private final CuratorFramework curatorFramework;

    public void createZNode(String path) {
        try {
            CuratorOp op = this.curatorFramework.transactionOp().create()
                    .withMode(CreateMode.PERSISTENT)
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                    .forPath(path);
            curatorFramework.transaction().forOperations(op);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createZNode(String path, byte[] value) {
        try {
            CuratorOp op = this.curatorFramework.transactionOp().create()
                    .withMode(CreateMode.PERSISTENT)
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                    .forPath(path, value);
            curatorFramework.transaction().forOperations(op);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createZNode(String path, String value) {
        createZNode(path, value.getBytes(UTF_8));
    }

    public void setZNodeValue(String path, String value) {
        setZNodeValue(path, value.getBytes(UTF_8));
    }

    public void setZNodeValue(String path, byte[] value) {
        try {
            this.curatorFramework.setData().forPath(path, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getZNodeValueAsString(String path) {
        return new String(getZNodeValue(path), UTF_8);
    }

    public byte[] getZNodeValue(String path) {
        try {
            return this.curatorFramework.getData().forPath(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean zNodeExists(String path) {
        try {
            return this.curatorFramework.checkExists()
                    .creatingParentsIfNeeded()
                    .forPath(path) != null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
