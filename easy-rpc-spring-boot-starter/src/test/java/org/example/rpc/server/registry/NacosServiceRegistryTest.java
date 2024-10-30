package org.example.rpc.server.registry;

import org.example.rpc.common.RegisterTypeEnums;
import org.example.rpc.common.ServiceInterfaceInfo;
import org.example.rpc.config.RpcRegistryProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NacosServiceRegistryTest {

    private NacosServiceRegistry nacosServiceRegistry;
    private ServiceInterfaceInfo serviceInterfaceInfo;

    @BeforeEach
    void setUp() {
        RpcRegistryProperties registryProperties = new RpcRegistryProperties();
        registryProperties.setType(RegisterTypeEnums.NACOS.name());
        registryProperties.setAddress("192.168.190.42:8848");
        nacosServiceRegistry = new NacosServiceRegistry(registryProperties);

        serviceInterfaceInfo = new ServiceInterfaceInfo("test", "127.0.0.1", 8080);
    }

    @Test
    void register() {
        assertDoesNotThrow(() -> nacosServiceRegistry.register(serviceInterfaceInfo));
    }
}