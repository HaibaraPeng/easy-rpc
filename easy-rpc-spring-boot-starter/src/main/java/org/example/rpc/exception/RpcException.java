package org.example.rpc.exception;

/**
 * Rpc异常
 *
 * @Author Roc
 * @Date 2024/10/29 15:42
 */
public class RpcException extends RuntimeException {
    public RpcException(String message) {
        super(message);
    }
}
