package com.jiazheng.test;

import com.jiazheng.rpc.api.HelloObject;
import com.jiazheng.rpc.api.HelloObject2;
import com.jiazheng.rpc.api.HelloService;
import com.jiazheng.rpc.api.HelloService2;
import com.jiazheng.rpc.RpcClientProxy;
import com.jiazheng.rpc.socket.client.SocketClient;

/**
 * 测试用消费者（客户端）
 * 客户端没有实现HelloService方法（即没有HelloServiceImpl），我们只有一个公共的接口HelloService
 * 所以创建一个动态代理对象proxy，调用其getProxy方法得到helloService对象
 *
 * @author Jamzy
 */
public class SocketTestClient {

    public static void main(String[] args) {
        SocketClient client = new SocketClient("127.0.0.1", 9000);
        RpcClientProxy proxy = new RpcClientProxy(client);
        //这个helloService就是代理对象
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloService2 helloService2 = proxy.getProxy(HelloService2.class);
        HelloObject object = new HelloObject(12, "This is a message");
        HelloObject2 object2 = new HelloObject2(12, "This is a message");
        //这里调用代理对象的方法时，会先调用代理类中的invoke方法
        String res = helloService.hello(object);
        String res2 = helloService2.hello2(object2);
        System.out.println(res);
        System.out.println(res2);
    }

}
