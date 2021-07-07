package com.jiazheng.test;

import com.jiazheng.rpc.api.HelloService;
import com.jiazheng.rpc.registry.DefaultServiceRegistry;
import com.jiazheng.rpc.registry.ServiceRegistry;
import com.jiazheng.rpc.server.RpcServer;

/**
 * 测试用服务提供方（服务端）
 * @author Jamzy
 */
public class TestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        RpcServer rpcServer = new RpcServer(serviceRegistry);
        rpcServer.start(9000);
    }

}