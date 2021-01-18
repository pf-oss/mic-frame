package com.mic.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Description:
 * @author: pf
 * @create: 2021/1/14 11:47
 */
public interface MicUserDetailsService extends UserDetailsService {
    /**
     * 根据电话号码查询用户
     *
     * @param mobile
     * @return
     */
    UserDetails loadUserByMobile(String mobile);
}
