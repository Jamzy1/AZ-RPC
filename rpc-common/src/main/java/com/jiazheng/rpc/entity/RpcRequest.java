package com.jiazheng.rpc.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 客户端调用服务端的请求体：用于唯一确定具体要调用服务端的哪一个方法
 *
 * @author Jamzy
 *
 */
@Data
//@Builder        //builder注解是为了后面代理对象被调用时方便生成RpcRequest对象
@AllArgsConstructor
@NoArgsConstructor
public class RpcRequest implements Serializable {

    /**
     * 待调用接口名称
     */
    private String interfaceName;
    /**
     * 待调用方法名称
     */
    private String methodName;
    /**
     * 调用方法的参数
     */
    private Object[] parameters;
    /**
     * 调用方法的参数类型
     */
    private Class<?>[] paramTypes;

}
