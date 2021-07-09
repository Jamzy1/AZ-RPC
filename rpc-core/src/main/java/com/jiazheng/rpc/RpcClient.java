package com.jiazheng.rpc;

import com.jiazheng.rpc.entity.RpcRequest;

/**
 * 客户端类通用接口
 * @author Jamzy
 */
public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);
}
