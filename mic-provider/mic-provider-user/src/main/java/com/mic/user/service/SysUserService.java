package com.mic.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mic.user.model.bean.SysUser;
import com.mic.user.api.model.vo.LoginAppUser;
import com.mic.user.api.model.vo.SysUserVo;

/**
 * @Description:
 * @author: pf
 * @create: 2021/1/12 15:06
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    SysUserVo selectByUsername(String username);

    LoginAppUser findByUsername(String username);

    LoginAppUser findByOpenId(String openId);

    LoginAppUser findByMobile(String phone);

}