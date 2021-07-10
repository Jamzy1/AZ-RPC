package com.jiazheng.rpc.provider;


import com.jiazheng.rpc.enumeration.RpcError;
import com.jiazheng.rpc.exception.RpcException;
import com.jiazheng.rpc.registry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的服务注册表,保存服务端本地服务
 * 调用方法时服务表等都已经初始化了饿汉式加载
 * @author Jamzy
 */
public class ServiceProviderImpl implements ServiceProvider {

    private static final Logger logger = LoggerFactory.getLogger(ServiceProviderImpl.class);

    //保存服务名与提供服务的对象的对应关系
    //默认采用这个对象实现的接口的完整类名作为服务名
    //一个服务名（接口）只能有一个对象提供服务
    private static final Map<String, Object> serviceMap = new ConcurrentHashMap<>();

    //已注册服务的集合
    private static final Set<String> registeredService =
            ConcurrentHashMap.newKeySet();


    @Override
    public <T> void addServiceProvider(T service, Class<T> serviceClass) {
        String serviceName = serviceClass.getCanonicalName();
        if (registeredService.contains(serviceName)) return;
        registeredService.add(serviceName);
        serviceMap.put(serviceName, service);
        logger.info("向接口: {} 注册服务: {}", service.getClass().getInterfaces(), serviceName);
    }

    @Override
    public Object getServiceProvider(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if (service == null){
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return service;
    }
}
