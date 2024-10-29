package org.example.rpc.common;

import lombok.Data;

import java.util.UUID;

/**
 * 服务提供者对外暴露的信息
 *
 * @Author Roc
 * @Date 2024/10/29 14:57
 */
@Data
public class ServiceInterfaceInfo {

    /**
     * 服务名称(接口全限定名)
     */
    private String serviceName;

    /**
     * 实例id，每个服务实例不一样
     */
    private String instanceId;

    /**
     * 服务实例ip地址，每个实例不一样
     */
    private String ip;

    /**
     * 服务实例端口，每个实例一样
     */
    private int port;

    /**
     * 服务实例对应的实现类，用于后续反射调用
     */
    private Class<?> clazz;

    /**
     * 服务实例对应的实现类实例，用于后续反射调用
     */
    private Object obj;

    public ServiceInterfaceInfo() {
    }

    public ServiceInterfaceInfo(String serviceName, String ip, int port, Class<?> clazz, Object obj) {
        this.serviceName = serviceName;
        this.instanceId = UUID.randomUUID().toString();
        this.ip = ip;
        this.port = port;
        this.clazz = clazz;
        this.obj = obj;
    }
}
