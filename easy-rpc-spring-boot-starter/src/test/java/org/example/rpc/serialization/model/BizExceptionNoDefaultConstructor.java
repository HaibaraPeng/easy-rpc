package org.example.rpc.serialization.model;

/**
 * @Author Roc
 * @Date 2024/11/14 11:29
 */
public class BizExceptionNoDefaultConstructor extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BizExceptionNoDefaultConstructor(String message) {
        super(message);
    }
}
