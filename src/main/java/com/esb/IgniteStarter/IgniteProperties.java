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
}
