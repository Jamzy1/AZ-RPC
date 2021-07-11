package com.jiazheng.test;

import com.jiazheng.rpc.annotation.Service;
import com.jiazheng.rpc.api.HelloObject;
import com.jiazheng.rpc.api.HelloService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//  在服务端这边对通用接口做一个简单的实现，返回一个字符串（只有服务端有接口的实现方法）
@Service
public class HelloServiceImpl implements HelloService {

    private static final Logger logger =
            LoggerFactory.getLogger(HelloServiceImpl.class);
    @Override
    public String hello(HelloObject object) {
        logger.info("接收到消息：{}", object.getMessage());
        return "这是Impl1方法";
    }
}
