package org.example.rpc.serialization;

import org.example.rpc.common.RpcRequest;
import org.example.rpc.common.RpcResponse;

/**
 * 消息协议
 *
 * @Author Roc
 * @Date 2024/10/29 15:11
 */
public interface MessageProtocol {

    /**
     * 编码请求消息
     *
     * @param request
     * @return
     * @throws Exception
     */
    byte[] marshallingReqMessage(RpcRequest request) throws Exception;

    /**
     * 解码请求消息
     *
     * @param data
     * @return
     * @throws Exception
     */
    RpcRequest unmarshallingReqMessage(byte[] data) throws Exception;

    /**
     * 编码响应消息
     *
     * @param response
     * @return
     * @throws Exception
     */
    byte[] marshallingRespMessage(RpcResponse response) throws Exception;

    /**
     * 解码响应消息
     *
     * @param data
     * @return
     * @throws Exception
     */
    RpcResponse unmarshallingRespMessage(byte[] data) throws Exception;
}
