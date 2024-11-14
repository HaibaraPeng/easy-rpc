package org.example.rpc.serialization.model;

/**
 * @Author Roc
 * @Date 2024/11/14 11:27
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BizException(String message) {
        super(message);
    }

    public BizException() {
    }
}
