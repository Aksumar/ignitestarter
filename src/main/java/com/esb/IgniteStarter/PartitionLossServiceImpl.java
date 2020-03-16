package com.esb.IgniteStarter;

import org.apache.ignite.services.Service;
import org.apache.ignite.services.ServiceContext;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PartitionLossServiceImpl implements Service, PartitionLossService {

    private ConcurrentMap<String, LinkedList<Integer>> lostPartitions;
    /** Service name. */
    private String svcName;

    @Override
    public void addPartition(String cache, Integer partitionNumber) {
        System.out.println(cache + "\t" + partitionNumber);
        lostPartitions.computeIfAbsent(cache, k ->  new LinkedList<>()).add(partitionNumber);
    }

    @Override
    public void cancel(ServiceContext ctx) {
        System.out.println("Service was cancelled: " + svcName);
    }

    @Override
    public void init(ServiceContext ctx) throws Exception {
        lostPartitions = new ConcurrentHashMap<>();

        svcName = ctx.name();
        System.out.println("Service was initialized: " + svcName);
    }

    @Override
    public void execute(ServiceContext ctx) throws Exception {
        System.out.println("Executing distributed service: " + svcName);
    }

    public void toStr() {
        for (String s : lostPartitions.keySet()) {
            System.out.println(s + " " + lostPartitions.get(s).size());
        }

        System.out.println();
    }
}
