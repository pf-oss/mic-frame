package com.mic.service.fallback;


import com.mic.model.dto.SysUserDto;
import com.mic.model.vo.LoginAppUser;
import com.mic.model.vo.SysUserVo;
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

        return new UserService() {
            @Override
            public SysUserDto getUser() {
                log.error("通过用户名查询用户异常:");
                return null;
            }

            @Override
            public SysUserVo selectByUsername(String username) {
                log.error("通过用户名查询用户异常:");
                return null;
            }

            @Override
            public LoginAppUser findByUsername(String username) {
                log.error("通过用户名查询用户异常:");
                return null;
            }

            @Override
            public LoginAppUser findByMobile(String mobile) {
                log.error("通过用户名查询用户异常:");
                return null;
            }

            @Override
            public LoginAppUser findByOpenId(String openId) {
                log.error("通过用户名查询用户异常:");
                return null;
            }
        };
    }
}
