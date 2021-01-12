package com.mic.service.fallback;


import com.mic.model.dto.SysUserDto;
import com.mic.service.UserService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description:
 * @author: pf
 * @create: 2021/1/12 17:07
 */
@Slf4j
public class UserServiceFallbackFactory implements FallbackFactory<UserService> {

    @Override
    public UserService create(Throwable throwable) {
        return () -> {
            log.error("通过用户名查询用户异常:{}", throwable);
            return new SysUserDto();
        };
    }
}
