package com.jiazheng.test;

import com.jiazheng.rpc.api.ByeService;
import com.jiazheng.rpc.serializer.CommonSerializer;
import com.jiazheng.rpc.transport.RpcClient;
import com.jiazheng.rpc.transport.RpcClientProxy;
import com.jiazheng.rpc.api.HelloObject;
import com.jiazheng.rpc.api.HelloService;
import com.jiazheng.rpc.transport.netty.client.NettyClient;

/**
 * 测试用Netty消费者
 * @author Jamzy
 */
public class NettyTestClient {

    public static void main(String[] args) {
        RpcClient client = new NettyClient(CommonSerializer.PROTOBUF_SERIALIZER);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
        ByeService byeService = rpcClientProxy.getProxy(ByeService.class);
        System.out.println(byeService.bye("Netty"));
    }

}
