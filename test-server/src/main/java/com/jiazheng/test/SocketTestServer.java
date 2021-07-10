package com.jiazheng.test;

import com.jiazheng.rpc.api.HelloService;
import com.jiazheng.rpc.serializer.KryoSerializer;
import com.jiazheng.rpc.transport.socket.server.SocketServer;

/**
 * 测试用服务提供方（服务端）
 * @author Jamzy
 */
public class SocketTestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl2();
        SocketServer socketServer = new SocketServer("127.0.0.1", 9998);
        socketServer.setSerializer(new KryoSerializer());
        socketServer.publishService(helloService, HelloService.class);
        socketServer.start();
    }

}