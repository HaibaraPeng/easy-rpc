package org.example.rpc.listener;

import lombok.extern.slf4j.Slf4j;
import org.example.rpc.annotation.ServiceExpose;
import org.example.rpc.annotation.ServiceReference;
import org.example.rpc.client.ClientProxyFactory;
import org.example.rpc.common.ServiceInterfaceInfo;
import org.example.rpc.config.RpcRegistryProperties;
import org.example.rpc.server.network.RpcServer;
import org.example.rpc.server.registry.ServiceRegistry;
import org.example.rpc.utils.IpUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 默认监听器，用于初始化RPC服务端或客户端
 *
 * @Author Roc
 * @Date 2024/10/30 17:11
 */
@Slf4j
public class DefaultRpcListener implements ApplicationListener<ContextRefreshedEvent> {

    private final RpcRegistryProperties rpcRegistryProperties;
    private final ServiceRegistry serviceRegistry;
    private final ClientProxyFactory clientProxyFactory;
    private final RpcServer rpcServer;

    public DefaultRpcListener(RpcRegistryProperties rpcRegistryProperties, ServiceRegistry serviceRegistry, ClientProxyFactory clientProxyFactory,
                              RpcServer rpcServer) {
        this.rpcRegistryProperties = rpcRegistryProperties;
        this.serviceRegistry = serviceRegistry;
        this.clientProxyFactory = clientProxyFactory;
        this.rpcServer = rpcServer;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        final ApplicationContext applicationContext = event.getApplicationContext();
        // root applicationContext执行
        if (applicationContext.getParent() == null) {
            // 初始化rpc服务端
            initRpcServer(applicationContext);
            // 初始化rpc客户端
            initRpcClient(applicationContext);
        }
    }

    private void initRpcServer(ApplicationContext applicationContext) {
        final Map<String, Object> beans = applicationContext.getBeansWithAnnotation(ServiceExpose.class);
        if (beans.isEmpty()) {
            return;
        }
        for (Object beanObj : beans.values()) {
            // 注册服务实例接口信息
            registerInstanceInterfaceInfo(beanObj);
        }

        // 启动网络通信服务器，监听客户端请求
        rpcServer.start();
    }

    private void registerInstanceInterfaceInfo(Object beanObj) {
        final Class<?>[] interfaces = beanObj.getClass().getInterfaces();
        if (interfaces.length == 0) {
            return;
        }

        Class<?> interfaceClazz = interfaces[0];

        try {
            serviceRegistry.register(new ServiceInterfaceInfo(interfaceClazz.getName(), IpUtil.getLocalIp(),
                    rpcRegistryProperties.getExposePort(), interfaceClazz, beanObj));
        } catch (Exception e) {
            log.error("Fail to register service: {}", e.getMessage());
        }
    }

    private void initRpcClient(ApplicationContext applicationContext) {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            Class<?> clazz = applicationContext.getType(beanName);
            if (clazz == null) {
                continue;
            }

            Field[] fields = clazz.getDeclaredFields();
            // 遍历@ServiceReference属性
            for (Field field : fields) {
                final ServiceReference serviceReference = field.getAnnotation(ServiceReference.class);
                if (serviceReference == null) {
                    continue;
                }

                try {
                    field.setAccessible(true);
                    field.set(applicationContext.getBean(beanName), clientProxyFactory.getProxyInstance(field.getType()));
                } catch (IllegalAccessException e) {
                    log.error(String.format("Fail to inject service, bean.name: %s", beanName), e);
                }
            }
        }
    }
}
