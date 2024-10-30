package org.example.rpc.common;

/**
 * 注册中心枚举
 *
 * @Author Roc
 * @Date 2024/10/29 16:00
 */
public enum RegisterTypeEnums {

    NACOS("nacos");

    private final String name;

    RegisterTypeEnums(String name) {
        this.name = name;
    }

}
