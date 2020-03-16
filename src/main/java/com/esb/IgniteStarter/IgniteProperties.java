package com.esb.IgniteStarter;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "server")
public class IgniteProperties {
    private List<String> ipFinderAddress;
    private String localAddress;
    private int discoveryPort;
    private int communicationPort;
    private int portRange;
    private boolean client;
    private boolean zookeeper;
    private List<String> zkConnectionString;
    private long sessionTimeout;
    private long joinTimeout;

    public List<String> getIpFinderAddress() {
        return ipFinderAddress;
    }

    public void setIpFinderAddress(List<String> ipFinderAddress) {
        this.ipFinderAddress = ipFinderAddress;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public int getDiscoveryPort() {
        return discoveryPort;
    }

    public void setDiscoveryPort(int discoveryPort) {
        this.discoveryPort = discoveryPort;
    }

    public int getCommunicationPort() {
        return communicationPort;
    }

    public void setCommunicationPort(int communicationPort) {
        this.communicationPort = communicationPort;
    }

    public int getPortRange() {
        return portRange;
    }

    public void setPortRange(int portRange) {
        this.portRange = portRange;
    }

    public boolean isClient() {
        return client;
    }

    public void setClient(boolean client) {
        this.client = client;
    }

    public boolean isZookeeper() {
        return zookeeper;
    }

    public void setZookeeper(boolean zookeeper) {
        this.zookeeper = zookeeper;
    }

    public List<String> getZkConnectionString() {
        return zkConnectionString;
    }

    public void setZkConnectionString(List<String> zkConnectionString) {
        this.zkConnectionString = zkConnectionString;
    }

    public long getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(long sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public long getJoinTimeout() {
        return joinTimeout;
    }

    public void setJoinTimeout(long joinTimeout) {
        this.joinTimeout = joinTimeout;
    }
}
