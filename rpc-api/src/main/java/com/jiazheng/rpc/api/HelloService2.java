package com.jiazheng.rpc.api;


/**
 * rpc-api中都是可以公共调用的接口，用于被服务端实现，和客户端动态代理获得
 * 用于测试的api接口
 *
 * @author Jamzy
 */
public interface HelloService2 {

    String hello2(HelloObject2 object);
}
