package com.mic.user.api.service;

import com.mic.base.ServiceNameConstants;
import com.mic.user.api.model.dto.SysUserDto;
import com.mic.user.api.model.vo.LoginAppUser;
import com.mic.user.api.model.vo.SysUserVo;
//import com.mic.user.api.service.fallback.UserServiceFallbackFactory;
import com.mic.user.api.service.fallback.UserServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @Description:
 * @author: pf
 * @create: 2021/1/12 17:15
 */
@FeignClient(name = ServiceNameConstants.MIC_PROVIDER_USER, fallbackFactory = UserServiceImpl.class, decode404 = true)
public interface UserService {

    /**
     * @Description:
     * @return: com.mic.model.dto.SysUserVo
     */
    @GetMapping(value = "/test/getUser")
    SysUserDto getUser();

    /**
     * @Description: 查询用户实体对象SysUser
     * @param username:
     * @return: SysUserVo
     */
    @GetMapping(value = "/user/name/{username}")
    SysUserVo selectByUsername(@PathVariable("username") String username);

    /**
     * @Description: 查询用户登录对象LoginAppUser
     * @param username:
     * @return: LoginAppUser
     */
    @GetMapping(value = "/user/login")
    LoginAppUser findByUsername(@RequestParam("username") String username);

    /**
     * @Description: 通过手机号查询用户、角色信息
     * @param mobile:
     * @return: SysUserVo
     */
    @GetMapping(value = "/user/mobile", params = "mobile")
    LoginAppUser findByMobile(String mobile);

    /**
     * @Description: 根据OpenId查询用户信息
     * @param openId:
     * @return: SysUserVo
     */
    @GetMapping(value = "/user/openId", params = "openId")
    LoginAppUser findByOpenId(String openId);




}
