package com.jiazheng.test;

import com.jiazheng.rpc.api.HelloObject;
import com.jiazheng.rpc.api.HelloObject2;
import com.jiazheng.rpc.api.HelloService2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloService2Impl implements HelloService2 {

    private static final Logger logger =
            LoggerFactory.getLogger(HelloService2Impl.class);

    @Override
    public String hello2(HelloObject2 object) {
        logger.info("接收到：{}",object.getMessage());
        return "这里调用的返回值，id="+object.getId();
    }
}
