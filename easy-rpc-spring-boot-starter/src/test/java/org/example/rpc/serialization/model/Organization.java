package org.example.rpc.serialization.model;

/**
 * @Author Roc
 * @Date 2024/11/18 15:37
 */
public class Organization<T> {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
