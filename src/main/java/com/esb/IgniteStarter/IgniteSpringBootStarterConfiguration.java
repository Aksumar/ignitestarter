package com.esb.IgniteStarter;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(IgniteProperties.class)
public class IgniteSpringBootStarterConfiguration {

    @Bean
    public IgniteConfiguration igniteConfiguration(IgniteProperties serverProperties) {
        final IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        igniteConfiguration.setClientMode(serverProperties.getIgniteCnfgClientMode());

        final TcpCommunicationSpi communicationSpi = new TcpCommunicationSpi();
        communicationSpi.setLocalAddress(serverProperties.getCommunicationSpi().getLocalAddress());
        communicationSpi.setLocalPort(serverProperties.getCommunicationSpi().getLocalPort());
        communicationSpi.setLocalPortRange(serverProperties.getCommunicationSpi().getPortRange());

        final TcpDiscoverySpi discoverySpi = new TcpDiscoverySpi();
        igniteConfiguration.setDiscoverySpi(discoverySpi);

        return igniteConfiguration;
    }

    @Bean
    public Ignite ignite(IgniteConfiguration igniteConfiguration)
    {
        Ignite ignite = Ignition.start(igniteConfiguration);
        return ignite;
    }
}


