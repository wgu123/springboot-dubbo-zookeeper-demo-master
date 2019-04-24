package com.wgu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wgu.api.service.DemoService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消费者控制层
 * @Author: w
 * @Date: 2019/4/24 14:21
 * @Version 1.0
 */
@RestController
public class DemoController {
    @Reference(version = "${demo.service.version}")
    private DemoService demoService;

    @RequestMapping("/sayHello/{name}")
    public String sayHello(@PathVariable("name") String name) {
        return demoService.sayHello(name);
    }
}
