package com.esb.IgniteStarter;

import org.apache.ignite.internal.*;
import org.apache.ignite.spi.discovery.zk.ZookeeperDiscoverySpi;

public class ZookeeperDiscoverySpiImpl extends ZookeeperDiscoverySpi {


    @Override
    public boolean allNodesSupport(IgniteFeatures feature) {
        return true;
    }
}
