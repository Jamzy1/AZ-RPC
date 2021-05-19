package com.jiazheng.test;

import com.jiazheng.rpc.api.HelloObject;
import com.jiazheng.rpc.api.HelloService;
import com.jiazheng.rpc.client.RpcClientProxy;

/**
 * 测试用消费者（客户端）
 * 客户端没有实现HelloService方法（即没有HelloServiceImpl），我们只有一个公共的接口HelloService
 * 所有创建一个动态代理对象proxy，调用其getProxy方法得到helloService对象
 *
 * @author Jamzy
 */
public class TestClient {

    public static void main(String[] args) {
        RpcClientProxy proxy = new RpcClientProxy("127.0.0.1", 9000);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }

}
