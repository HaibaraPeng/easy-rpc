package org.example.rpc.common;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 响应对象
 *
 * @Author Roc
 * @Date 2024/10/29 15:14
 */
@Data
public class RpcResponse implements Serializable {

    private Integer status;
    private Object data;
    private Map<String, String> headers = new HashMap<>();
    private Exception exception;
}
