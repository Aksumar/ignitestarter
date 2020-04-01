package com.esb.IgniteStarter;

import org.apache.ignite.*;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.events.*;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.apache.ignite.lang.IgnitePredicate;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.apache.ignite.spi.discovery.zk.ZookeeperDiscoverySpi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

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
        ZookeeperDiscoverySpi zkDiscoSpi = new ZookeeperDiscoverySpi();
        zkDiscoSpi.clientReconnectSupported();

        zkDiscoSpi.setZkConnectionString(
                String.join(",", igniteProperties.getZkConnectionString()));

        zkDiscoSpi.setSessionTimeout(igniteProperties.getSessionTimeout());

        zkDiscoSpi.setZkRootPath("/IgniteTestZK");
        zkDiscoSpi.setJoinTimeout(igniteProperties.getJoinTimeout());


        igniteConfiguration.setCommunicationSpi(communicationSpi);

        igniteConfiguration.setLifecycleBeans(new LifeCycleBeanImpl());
        if (igniteProperties.isZookeeper())
            igniteConfiguration.setDiscoverySpi(zkDiscoSpi);
        else
            igniteConfiguration.setDiscoverySpi(discoverySpi);


        igniteConfiguration.setIncludeEventTypes(EventType.EVT_NODE_LEFT,
                EventType.EVT_NODE_FAILED, EventType.EVT_CACHE_OBJECT_PUT);

        Ignite ignite = Ignition.start(igniteConfiguration);





        return ignite;
    }
}
