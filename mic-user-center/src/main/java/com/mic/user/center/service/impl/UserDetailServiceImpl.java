package com.mic.user.center.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mic.user.api.model.vo.LoginAppUser;
import com.mic.user.center.service.MicUserDetailsService;
import com.mic.user.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @Description:
 * @author: pf
 * @create: 2021/1/14 13:55
 */
@Slf4j
@Service
public class UserDetailServiceImpl implements MicUserDetailsService, SocialUserDetailsService {

    @Resource
    private UserService userService;

    @Override
    @JsonIgnore
    public UserDetails loadUserByUsername(String username) {
        LoginAppUser loginAppUser = userService.findByUsername(username);
        if (loginAppUser == null) {
            throw new InternalAuthenticationServiceException("用户名或密码错误");
        }
        return checkUser(loginAppUser);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String openId) {
        LoginAppUser loginAppUser = userService.findByOpenId(openId);
        return checkUser(loginAppUser);
    }

    @Override
    public UserDetails loadUserByMobile(String mobile) {
        LoginAppUser loginAppUser = userService.findByMobile(mobile);
        return checkUser(loginAppUser);
    }

    private LoginAppUser checkUser(LoginAppUser loginAppUser) {
        if (loginAppUser != null && !loginAppUser.isEnabled()) {
            throw new DisabledException("用户已作废");
        }
        return loginAppUser;
    }
}
