package com.jiazheng.rpc.transport;


import com.jiazheng.rpc.serializer.CommonSerializer;

/**
 * 服务器类通用接口
 * @author Jamzy
 */
public interface RpcServer {
    void start();

    void setSerializer(CommonSerializer serializer);

    <T> void publishService(T service, Class<T> serviceClass);
}
