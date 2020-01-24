package com.esb.IgniteStarter;

import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.plugin.segmentation.SegmentationPolicy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import javax.annotation.PostConstruct;

@ConfigurationProperties(prefix = "server")
public class IgniteProperties {
    private String cacheName;
    private Boolean igniteCnfgClientMode;
    private String ipFinderAddress;
    private SegmentationPolicy segmentationPolicy;
    private CacheMode cacheMode;
    private CommunicationSpi communicationSpi;

    public static class CommunicationSpi {
        private String localAddress;
        private int localPort;
        private int portRange;

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
    }

    public CommunicationSpi getCommunicationSpi() {
        return communicationSpi;
    }

    public void setCommunicationSpi(CommunicationSpi communicationSpi) {
        this.communicationSpi = communicationSpi;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheMode(CacheMode cacheMode) {
        this.cacheMode = cacheMode;
    }

    public SegmentationPolicy getSegmentationPolicy() {
        return this.segmentationPolicy;
    }

    public void setSegmentationPolicy(SegmentationPolicy segmentationPolicy) {
        this.segmentationPolicy = segmentationPolicy;
    }

    public CacheMode getCacheMode() {
        return cacheMode;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public Boolean getIgniteCnfgClientMode() {
        return igniteCnfgClientMode;
    }

    public void setIgniteCnfgClientMode(Boolean igniteCnfgClientMode) {
        this.igniteCnfgClientMode = igniteCnfgClientMode;
    }

    public String getIpFinderAddress() {
        return ipFinderAddress;
    }

    public void setIpFinderAddress(String ipFinderAddress) {
        this.ipFinderAddress = ipFinderAddress;
    }


    @PostConstruct
    public static void post() {
        System.out.println("------------------------Server properties are created------------------------");
    }
}
