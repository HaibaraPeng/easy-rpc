package org.example.rpc.client.discovery;

import org.example.rpc.common.RegisterTypeEnums;
import org.example.rpc.config.RpcDiscoveryProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class NacosServiceDiscoveryTest {

    private NacosServiceDiscovery nacosServiceDiscovery;

    @BeforeEach
    void setUp() {
        RpcDiscoveryProperties properties = new RpcDiscoveryProperties();
        properties.setType(RegisterTypeEnums.NACOS.name());
        properties.setAddress("192.168.190.42:8848");
        nacosServiceDiscovery = new NacosServiceDiscovery(properties);
    }

    @Test
    void selectOneInstance() {
        assertNotNull(nacosServiceDiscovery.selectOneInstance("org.example.rpc.example.provider.api.ExampleOneService"));
    }
}