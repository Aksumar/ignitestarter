package com.esb.IgniteStarter;

import org.apache.ignite.IgniteException;
import org.apache.ignite.lifecycle.LifecycleBean;
import org.apache.ignite.lifecycle.LifecycleEventType;

import java.time.LocalTime;

public class LifeCycleBeanImpl implements LifecycleBean {
    @Override
    public void onLifecycleEvent(LifecycleEventType evt) throws IgniteException {
        if (evt == LifecycleEventType.BEFORE_NODE_STOP) {
            System.out.println("\t\t\t\t[ " + LocalTime.now().toString() + " ] BEFORE NODE STOP");
        }

        if (evt == LifecycleEventType.AFTER_NODE_STOP) {
            System.out.println("\t\t\t\t[ " + LocalTime.now().toString() + " ] AFTER NODE STOP");
        }
    }
}
