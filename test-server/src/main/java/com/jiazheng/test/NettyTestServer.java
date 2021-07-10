package com.jiazheng.test;


import com.jiazheng.rpc.api.HelloService;
import com.jiazheng.rpc.serializer.KryoSerializer;
import com.jiazheng.rpc.transport.netty.server.NettyServer;
import com.jiazheng.rpc.provider.ServiceProviderImpl;
import com.jiazheng.rpc.registry.ServiceRegistry;

/**
 * 测试用Netty服务提供者（服务端）
 * @author Jamzy
 */
public class NettyTestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        NettyServer server = new NettyServer("127.0.0.1",9990);
        server.setSerializer(new KryoSerializer());
        server.publishService(helloService,HelloService.class);
    }

}
