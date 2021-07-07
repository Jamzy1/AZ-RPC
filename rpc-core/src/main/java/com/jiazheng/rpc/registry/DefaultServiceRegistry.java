package com.jiazheng.rpc.registry;


import com.jiazheng.rpc.enumeration.RpcError;
import com.jiazheng.rpc.enumeration.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的服务注册表
 * @author Jamzy
 */
public class DefaultServiceRegistry implements ServiceRegistry{

    private static final Logger logger = LoggerFactory.getLogger(DefaultServiceRegistry.class);

    //保存服务名与提供服务的对象的对应关系
    //默认采用这个对象实现的接口的完整类名作为服务名
    //一个服务名（接口）只能有一个对象提供服务
    private final Map<String, Object> serviceMap = new ConcurrentHashMap<>();

    //已注册服务的集合
    private final Set<String> registeredService = ConcurrentHashMap.newKeySet();


    @Override
    public synchronized  <T> void register(T service) {
        String serviceName = service.getClass().getCanonicalName();
        if (registeredService.contains(serviceName)) return;
        registeredService.add(serviceName);

        //interfaces是服务继承的所有接口
        Class<?>[] interfaces = service.getClass().getInterfaces();

        //注册的服务未实现接口
        if (interfaces.length == 0){
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        }
        for (Class<?> i : interfaces){
            serviceMap.put(i.getCanonicalName(), service);
        }
        logger.info("向接口：{} 注册服务：{}", interfaces, serviceName);
    }

    @Override
    public synchronized Object getService(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if (service == null){
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return service;
    }
}
