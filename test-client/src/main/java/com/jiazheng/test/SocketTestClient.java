package com.jiazheng.test;

import com.jiazheng.rpc.api.HelloObject;
import com.jiazheng.rpc.api.HelloService;
import com.jiazheng.rpc.serializer.CommonSerializer;
import com.jiazheng.rpc.transport.RpcClientProxy;
import com.jiazheng.rpc.transport.socket.client.SocketClient;

/**
 * 测试用消费者（客户端）
 * 客户端没有实现HelloService方法（即没有HelloServiceImpl），我们只有一个公共的接口HelloService
 * 所以创建一个动态代理对象proxy，调用其getProxy方法得到helloService对象
 *
 * @author Jamzy
 */
public class SocketTestClient {

    public static void main(String[] args) {
        SocketClient client = new SocketClient(CommonSerializer.KRYO_SERIALIZER);
        RpcClientProxy proxy = new RpcClientProxy(client);
        //这个helloService就是代理对象
        HelloService helloService = proxy.getProxy(HelloService.class);

        HelloObject object = new HelloObject(12, "This is a message");

        //这里调用代理对象的方法时，会先调用代理类中的invoke方法
        String res = helloService.hello(object);

        System.out.println(res);

    }

}
