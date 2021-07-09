package com.jiazheng.test;

import com.jiazheng.rpc.RpcClient;
import com.jiazheng.rpc.RpcClientProxy;
import com.jiazheng.rpc.api.HelloObject;
import com.jiazheng.rpc.api.HelloService;
import com.jiazheng.rpc.netty.client.NettyClient;

/**
 * 测试用Netty消费者
 * @author Jamzy
 */
public class NettyTestClient {

    public static void main(String[] args) {
        RpcClient client = new NettyClient("127.0.0.1", 9999);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);

    }

}
