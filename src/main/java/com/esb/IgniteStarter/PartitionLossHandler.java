package com.esb.IgniteStarter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PartitionLossHandler {

    public ConcurrentMap<String, LinkedList<Integer>> lostPartitions;

    public PartitionLossHandler() {
        lostPartitions = new ConcurrentHashMap<>();
    }

    public void addPartition(String cache, Integer partitionNumber)
    {
        lostPartitions.computeIfAbsent(cache, k ->  new LinkedList<>()).add(partitionNumber);
    }

    public void toStr() {
        for (String s : lostPartitions.keySet()) {
            System.out.println(s + " " + lostPartitions.get(s).size());
        }

        System.out.println();

    }
}
