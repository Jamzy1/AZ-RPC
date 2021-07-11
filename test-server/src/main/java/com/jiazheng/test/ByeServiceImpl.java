package com.jiazheng.test;

import com.jiazheng.rpc.annotation.Service;
import com.jiazheng.rpc.api.ByeService;

@Service
public class ByeServiceImpl implements ByeService {

    @Override
    public String bye(String name) {
        return "bye, " + name;
    }
}
