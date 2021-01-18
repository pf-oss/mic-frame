package com.mic.controller;

import com.alibaba.fastjson.JSON;
import com.mic.service.UserService;
import com.mic.utils.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RedisRepository redisRepository;

    @GetMapping("/test")
    public String getUser() {
        redisRepository.set("1", userService.getUser());

       return JSON.toJSONString(userService.getUser());
    }


    @GetMapping("/redis")
    public String getRedis() {
//        redisRepository.setStr("1", "张三");
//       return redisRepository.getStr("1");
       return JSON.toJSONString(redisRepository.get("1"));
    }


}
