package com.esb.IgniteStarter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "server")
public class IgniteProperties {
    private static final Logger logger = LoggerFactory.getLogger(IgniteProperties.class);
    private String ipFinderAddress;
    private String localAddress;
    private int localPort;
    private int portRange;
    private boolean client;

    public String getIpFinderAddress() {
        return ipFinderAddress;
    }

    public void setIpFinderAddress(String ipFinderAddress) {
        this.ipFinderAddress = ipFinderAddress;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public int getLocalPort() {
        return localPort;
    }

    public void setLocalPort(int localPort) {
        this.localPort = localPort;
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
