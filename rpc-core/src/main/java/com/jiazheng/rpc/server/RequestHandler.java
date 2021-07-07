package com.jiazheng.rpc.server;

import com.jiazheng.rpc.entity.RpcRequest;
import com.jiazheng.rpc.entity.RpcResponse;
import com.jiazheng.rpc.enumeration.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 进行过程调用的处理器
 *
 * RequestHandlerThread 只是一个线程，从ServiceRegistry 获取到提供服务的对象后，就会把 RpcRequest 和服务对象直接交给 RequestHandler 去处理，
 * 反射等过程被放到了 RequestHandler 里。
 *
 * @author Jamzy
 */
public class RequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public Object handle(RpcRequest rpcRequest, Object service) {
        Object result = null;
        try {
            result = invokeTargetMethod(rpcRequest, service);
            logger.info("服务:{} 成功调用方法:{}", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("调用或发送时有错误发生：", e);
        }
        return result;
    }

    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service) throws IllegalAccessException, InvocationTargetException {
        Method method;
        try {
            method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
        } catch (NoSuchMethodException e) {
            return RpcResponse.fail(ResponseCode.METHOD_NOT_FOUND);
        }
        //通过反射进行方法调用
        return method.invoke(service, rpcRequest.getParameters());
    }
}
