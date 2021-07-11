package com.jiazheng.rpc.transport;

import com.jiazheng.rpc.entity.RpcRequest;
import com.jiazheng.rpc.serializer.CommonSerializer;

/**
 * 客户端类通用接口
 * @author Jamzy
 */
public interface RpcClient {

    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;


    Object sendRequest(RpcRequest rpcRequest);
}
