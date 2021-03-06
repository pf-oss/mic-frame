package com.mic.user.controller;


import com.mic.user.model.bean.SysUser;
import com.mic.user.dao.SysUserDao;
import com.mic.user.api.model.dto.SysUserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author: pf
 * @create: 2021/1/12 16:23
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private SysUserDao sysUserDao;

    @GetMapping("/getUser")
    public SysUserDto getUser() {
        SysUser sysUser = sysUserDao.selectById("1");
        SysUserDto sysUserDto = new SysUserDto();
        BeanUtils.copyProperties(sysUser, sysUserDto);
        return sysUserDto;
    }



}
