package com.jiazheng.rpc.transport;

import com.jiazheng.rpc.entity.RpcRequest;
import com.jiazheng.rpc.serializer.CommonSerializer;

/**
 * 客户端类通用接口
 * @author Jamzy
 */
public interface RpcClient {


    Object sendRequest(RpcRequest rpcRequest);

    void setSerializer(CommonSerializer serializer);
}
