package com.jiazheng.test;


import com.jiazheng.rpc.annotation.ServiceScan;
import com.jiazheng.rpc.serializer.CommonSerializer;
import com.jiazheng.rpc.transport.RpcServer;
import com.jiazheng.rpc.transport.netty.server.NettyServer;
/**
 * 测试用Netty服务提供者（服务端）
 * @author Jamzy
 */
@ServiceScan
public class NettyTestServer {

    public static void main(String[] args) {
        RpcServer server = new NettyServer("127.0.0.1", 9990,CommonSerializer.PROTOBUF_SERIALIZER);
        server.start();
    }

}
