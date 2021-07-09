package com.jiazheng.test;

import com.jiazheng.rpc.api.HelloService;
import com.jiazheng.rpc.registry.DefaultServiceRegistry;
import com.jiazheng.rpc.registry.ServiceRegistry;
import com.jiazheng.rpc.socket.server.SocketServer;

/**
 * 测试用服务提供方（服务端）
 * @author Jamzy
 */
public class SocketTestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        HelloService2Impl helloService2 = new HelloService2Impl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        //将服务注册进注册表
        serviceRegistry.register(helloService);
        serviceRegistry.register(helloService2);
        SocketServer socketServer = new SocketServer(serviceRegistry);
        socketServer.start(9000);
    }

}