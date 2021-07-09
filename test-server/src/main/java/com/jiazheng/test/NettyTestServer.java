package com.jiazheng.test;


import com.jiazheng.rpc.api.HelloService;
import com.jiazheng.rpc.netty.server.NettyServer;
import com.jiazheng.rpc.registry.DefaultServiceRegistry;
import com.jiazheng.rpc.registry.ServiceRegistry;

/**
 * 测试用Netty服务提供者（服务端）
 * @author Jamzy
 */
public class NettyTestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        NettyServer server = new NettyServer();
        server.start(9999);
    }

}
