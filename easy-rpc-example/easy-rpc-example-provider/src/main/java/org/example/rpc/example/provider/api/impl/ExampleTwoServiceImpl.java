package org.example.rpc.example.provider.api.impl;

import org.example.rpc.annotation.ServiceExpose;
import org.example.rpc.example.provider.api.ExampleTwoService;

/**
 * @Author Roc
 * @Date 2024/10/31 15:22
 */
@ServiceExpose
public class ExampleTwoServiceImpl implements ExampleTwoService {
    @Override
    public String testOne(String name) {
        return name + ",调用了ExampleTwoService的testOne方法";
    }

    @Override
    public String testTwo(String name) {
        return name + ",调用了ExampleTwoService的testTwo方法";
    }
}
