package com.jiazheng.test;

import com.jiazheng.rpc.api.HelloService;
import com.jiazheng.rpc.api.HelloService2;
import com.jiazheng.rpc.serializer.KryoSerializer;
import com.jiazheng.rpc.transport.socket.server.SocketServer;

/**
 * 测试用服务提供方（服务端）
 * @author Jamzy
 */
public class SocketTestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        HelloService2 helloService2 = new HelloService2Impl();
        SocketServer socketServer = new SocketServer("127.0.0.1", 9998);
        socketServer.setSerializer(new KryoSerializer());
        socketServer.publishService(helloService, HelloService.class);
        socketServer.publishService(helloService2, HelloService2.class);
        socketServer.start();
    }

}