package com.esb.IgniteStarter;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({IgniteProperties.class})
public class IgniteSpringBootStarterConfiguration {

    @Bean
    public Ignite ignite(IgniteProperties clientProperties) {

        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        igniteConfiguration.setClientMode(clientProperties.isClient());

        //указываем, на каком хост-порт будет установлена сама нода
        TcpDiscoverySpi discoverySpi = new TcpDiscoverySpi();
        discoverySpi.setLocalAddress(clientProperties.getLocalAddress());
        discoverySpi.setLocalPort(clientProperties.getLocalPort());
        discoverySpi.setLocalPortRange(clientProperties.getPortRange());

        //1024 раньше было по дефолту
        TcpCommunicationSpi communicationSpi = new TcpCommunicationSpi();
        communicationSpi.setMessageQueueLimit(1024);

        //указываем к какому набору хост-порт хотим подключиться
        TcpDiscoveryVmIpFinder ipFinder=new TcpDiscoveryVmIpFinder();
        ipFinder.setAddresses(clientProperties.getIpFinderAddress());
        discoverySpi.setIpFinder(ipFinder);


        igniteConfiguration.setDiscoverySpi(discoverySpi);
        igniteConfiguration.setCommunicationSpi(communicationSpi);
        return  Ignition.start(igniteConfiguration);
    }
}
