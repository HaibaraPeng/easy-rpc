package org.example.rpc.client.discovery;

/**
 * 注册中心枚举
 *
 * @Author Roc
 * @Date 2024/10/29 16:00
 */
public enum RegisterEnums {

    NACOS("nacos");

    private final String name;

    RegisterEnums(String name) {
        this.name = name;
    }

}
