package com.jiazheng.test;

import com.jiazheng.rpc.annotation.ServiceScan;
import com.jiazheng.rpc.serializer.CommonSerializer;
import com.jiazheng.rpc.transport.RpcServer;
import com.jiazheng.rpc.transport.socket.server.SocketServer;

/**
 * 测试用服务提供方（服务端）
 * @author Jamzy
 */
@ServiceScan
public class SocketTestServer {

    public static void main(String[] args) {
        RpcServer server = new SocketServer("127.0.0.1", 9998, CommonSerializer.HESSIAN_SERIALIZER);
        server.start();
    }

}