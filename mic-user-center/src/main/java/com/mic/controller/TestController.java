package com.mic.controller;

import com.alibaba.fastjson.JSON;
import com.mic.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description:
 * @author: pf
 * @create: 2021/1/12 17:30
 */
@RestController
@RequestMapping("/demo")
public class TestController {

    @Resource
    private UserService userService;

    @GetMapping("/test")
    public String getUser() {
       return JSON.toJSONString(userService.getUser());
    }

}
