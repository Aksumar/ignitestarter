package com.esb.IgniteStarter;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;

@Configuration
@EnableConfigurationProperties({IgniteProperties.class})
public class IgniteSpringBootStarterConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(IgniteSpringBootStarterConfiguration.class);

    @Bean
    public Ignite ignite(IgniteProperties clientProperties) {
        logger.info("Start creating Ignite bean");

        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        igniteConfiguration.setClientMode(clientProperties.isClient());

        //указываем, на каком хост-порт будет установлена сама нода
        TcpDiscoverySpi discoverySpi = new TcpDiscoverySpi();
        discoverySpi.setLocalAddress(clientProperties.getLocalAddress());
        discoverySpi.setLocalPort(clientProperties.getLocalPort());
        discoverySpi.setLocalPortRange(clientProperties.getPortRange());

        //указываем к какому набору хост-порт хотим подключиться
        TcpCommunicationSpi communicationSpi = new TcpCommunicationSpi();
        TcpDiscoveryVmIpFinder ipFinder=new TcpDiscoveryVmIpFinder();
        ipFinder.setAddresses(Arrays.asList(clientProperties.getIpFinderAddress()));
        discoverySpi.setIpFinder(ipFinder);

        igniteConfiguration.setDiscoverySpi(discoverySpi);
        return  Ignition.start(igniteConfiguration);
    }
}
