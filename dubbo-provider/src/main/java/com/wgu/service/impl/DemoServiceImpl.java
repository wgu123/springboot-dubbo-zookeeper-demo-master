package com.wgu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wgu.api.service.DemoService;

/**
 * @Author: w
 * @Date: 2019/4/24 14:12
 * @Version 1.0
 */
@Service(version = "${demo.service.version}")
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name + " this is dubbo-zookeeper-spring boot-demo";
    }
}
