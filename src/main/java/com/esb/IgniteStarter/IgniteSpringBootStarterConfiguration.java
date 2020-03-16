package com.esb.IgniteStarter;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.events.*;
import org.apache.ignite.lang.IgnitePredicate;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLTransactionRollbackException;
import java.util.*;

@Configuration
@EnableConfigurationProperties({IgniteProperties.class})
public class IgniteSpringBootStarterConfiguration {

    @Bean
    public Ignite ignite(IgniteProperties igniteProperties) {

        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        igniteConfiguration.setClientMode(igniteProperties.isClient());

        //указываем, на каком хост-порт будет установлена сама нода
        TcpDiscoverySpi discoverySpi = new TcpDiscoverySpi();
        discoverySpi.setLocalAddress(igniteProperties.getLocalAddress());
        discoverySpi.setLocalPort(igniteProperties.getDiscoveryPort());
        discoverySpi.setLocalPortRange(igniteProperties.getPortRange());

        //1024 раньше было по дефолту
        TcpCommunicationSpi communicationSpi = new TcpCommunicationSpi();
        communicationSpi.setMessageQueueLimit(1024);
        communicationSpi.setLocalPort(igniteProperties.getCommunicationPort());
        communicationSpi.setLocalAddress(igniteProperties.getLocalAddress());
        communicationSpi.setLocalPortRange(igniteProperties.getPortRange());

        //указываем к какому набору хост-порт хотим подключиться
        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
        ipFinder.setAddresses(igniteProperties.getIpFinderAddress());
        discoverySpi.setIpFinder(ipFinder);

        //Если активирован zookeeper:
        ZookeeperDiscoverySpiImpl zkDiscoSpi = new ZookeeperDiscoverySpiImpl();
        zkDiscoSpi.clientReconnectSupported();

        zkDiscoSpi.setZkConnectionString(
                String.join(",", igniteProperties.getZkConnectionString()));

        zkDiscoSpi.setSessionTimeout(igniteProperties.getSessionTimeout());

        zkDiscoSpi.setZkRootPath("/IgniteTestZK");
        zkDiscoSpi.setJoinTimeout(igniteProperties.getJoinTimeout());

        IgniteLogger log = new Slf4jLogger();

        igniteConfiguration.setGridLogger(log);


        igniteConfiguration.setCommunicationSpi(communicationSpi);

        if (igniteProperties.isZookeeper())
            igniteConfiguration.setDiscoverySpi(zkDiscoSpi);
        else
            igniteConfiguration.setDiscoverySpi(discoverySpi);

        igniteConfiguration.setIncludeEventTypes(EventType.EVT_CACHE_REBALANCE_PART_DATA_LOST);

        Ignite ignite = Ignition.start(igniteConfiguration);

        PartitionLossHandler partitionLossHandler = new PartitionLossHandler();

        IgnitePredicate<CacheRebalancingEvent> locLsnrDataLost = evt -> {

            partitionLossHandler.addPartition(evt.cacheName(), evt.partition());
            partitionLossHandler.toStr();


            return true; // Continue listening.
        };


        ignite.events().localListen(locLsnrDataLost,
                EventType.EVT_CACHE_REBALANCE_PART_DATA_LOST);


        return ignite;
    }
}
