package org.example.rpc.common;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求对象
 *
 * @Author Roc
 * @Date 2024/10/29 15:09
 */
@Data
public class RpcRequest implements Serializable {
    private String serviceName;
    private String methodName;
    private Map<String, String> headers = new HashMap<>();
    private Class<?>[] parameterTypes;
    private Object[] parameters;

    public RpcRequest() {
    }

    public RpcRequest(String serviceName, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
        this.serviceName = serviceName;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.parameters = parameters;
    }
}
