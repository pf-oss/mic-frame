package com.mic.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mic.model.bean.SysUser;
import com.mic.model.vo.LoginAppUser;
import com.mic.model.vo.SysUserVo;

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