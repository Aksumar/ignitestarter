package com.esb.IgniteStarter;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteEvents;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.events.DiscoveryEvent;
import org.apache.ignite.events.EventType;
import org.apache.ignite.lang.IgnitePredicate;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.apache.ignite.plugin.segmentation.SegmentationPolicy;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({IgniteProperties.class})
public class IgniteSpringBootStarterConfiguration {

    Logger logger = LoggerFactory.getLogger(IgniteSpringBootStarterConfiguration.class);

    @Bean
    public Ignite ignite(IgniteProperties clientProperties) {

        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        igniteConfiguration.setClientMode(clientProperties.isClient());

        //указываем, на каком хост-порт будет установлена сама нода
        TcpDiscoverySpi discoverySpi = new TcpDiscoverySpi();
        discoverySpi.setLocalAddress(clientProperties.getLocalAddress());
        discoverySpi.setLocalPort(clientProperties.getDiscoveryPort());
        discoverySpi.setLocalPortRange(clientProperties.getPortRange());

        //1024 раньше было по дефолту
        TcpCommunicationSpi communicationSpi = new TcpCommunicationSpi();
        communicationSpi.setMessageQueueLimit(1024);
        communicationSpi.setLocalPort(clientProperties.getCommunicationPort());
        communicationSpi.setLocalAddress(clientProperties.getLocalAddress());
        communicationSpi.setLocalPortRange(clientProperties.getPortRange());

        //указываем к какому набору хост-порт хотим подключиться
        TcpDiscoveryVmIpFinder ipFinder=new TcpDiscoveryVmIpFinder();
        ipFinder.setAddresses(clientProperties.getIpFinderAddress());
        discoverySpi.setIpFinder(ipFinder);

        IgniteLogger log = new Slf4jLogger();

        igniteConfiguration.setGridLogger(log);

        igniteConfiguration.setSegmentationPolicy(SegmentationPolicy.NOOP);
        igniteConfiguration.setDiscoverySpi(discoverySpi);
        igniteConfiguration.setCommunicationSpi(communicationSpi);

        IgnitePredicate<DiscoveryEvent> locLsnr = evt -> {
            logger.info("\n\n\tReceived event [evt=" + evt.name() + "] info : " + evt.shortDisplay() + "]\n");

            return true; // Continue listening.
        };

        Ignite ignite =  Ignition.start(igniteConfiguration);
        IgniteEvents evts = ignite.events();
        evts.localListen(locLsnr, EventType.EVT_NODE_JOINED, EventType.EVT_NODE_FAILED, EventType.EVT_NODE_LEFT,
                EventType.EVT_NODE_SEGMENTED, EventType.EVT_CLIENT_NODE_DISCONNECTED, EventType.EVT_CLIENT_NODE_RECONNECTED);

        return ignite;
    }
}
