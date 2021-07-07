package com.jiazheng.rpc.client;

import com.jiazheng.rpc.entity.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jiazheng.rpc.entity.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * RPC客户端  没有接口的实现类，所以用动态代理生成实例
 * 客户端实现选择用动态代理是为了对send方法做增强，给这个切面织入远程通信逻辑
 *
 * @author Jamzy
 */
public class RpcClientProxy implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(RpcClientProxy.class);
    private String host;
    private int port;

    public RpcClientProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    //  Proxy类就是用来创建一个代理对象的类，这里使用最常用的newProxyInstance()方法来生成代理对象
    @SuppressWarnings("unchecked")      //忽略 unchecked 警告信息
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    //代理对象的方法被调用时，会先调用这个invoke方法。生成一个RpcRequest对象
    /**
     *
     * @param proxy:代理类代理的真实对象
     * @param method:我们所要调用某个对象真实的方法的Method对象
     * @param args:指代代理对象方法传递的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("调用方法: {}#{}", method.getDeclaringClass().getName(),
                method.getName());
        //构建rpcRequest
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameters(args)
                .paramTypes(method.getParameterTypes())
                .build();
        //通过sendRequest发送给服务端，并调用getData()获得响应数据
        RpcClient rpcClient = new RpcClient();
        return ((RpcResponse)rpcClient.sendRequest(rpcRequest, host, port)).getData();
    }
}
