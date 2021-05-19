package com.jiazheng.test;

import com.jiazheng.rpc.api.HelloObject;
import com.jiazheng.rpc.api.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//  在服务端这边对通用接口做一个简单的实现，返回一个字符串
public class HelloServiceImpl implements HelloService {

    private static final Logger logger =
            LoggerFactory.getLogger(HelloServiceImpl.class);
    @Override
    public String hello(HelloObject object) {
        logger.info("接收到：{}",object.getMessage());
        return "这里调用的返回值，id="+object.getId();
    }
}
