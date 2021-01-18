//package com.mic.service.fallback;
//
//
//import com.mic.model.dto.SysUserVo;
//import feign.hystrix.FallbackFactory;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
///**
// * @Description:
// * @author: pf
// * @create: 2021/1/12 17:07
// */
//@Slf4j
//@Component
//public class UserServiceFallback implements UserService {
//
//    @Override
//    public SysUserVo getUser() {
//        log.error("通过用户名查询用户异常:");
//        return null;
//    }
//}
