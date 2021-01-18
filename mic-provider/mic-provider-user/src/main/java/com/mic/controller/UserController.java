package com.mic.controller;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.mic.constant.constant.CommonConstant;
import com.mic.dao.SysUserDao;
import com.mic.model.bean.SysMenu;
import com.mic.model.bean.SysUser;
import com.mic.model.dto.SysUserDto;
import com.mic.model.vo.LoginAppUser;
import com.mic.model.vo.SysUserVo;
import com.mic.service.SysMenuService;
import com.mic.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @author: pf
 * @create: 2021/1/14 13:57
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysMenuService menuService;

    @Autowired
    private SysUserDao sysUserDao;

    /**
     * 查询用户实体对象SysUser
     */
    @GetMapping(value = "/name/{username}")
    public SysUserVo selectByUsername(@PathVariable String username) {
        return userService.selectByUsername(username);
    }

    /**
     * 查询用户登录对象LoginAppUser
     */
    @GetMapping(value = "/login")
    public LoginAppUser findByUsername(String username) {
        return userService.findByUsername(username);
    }

    /**
     * 通过手机号查询用户、角色信息
     *
     * @param mobile 手机号
     */
    @GetMapping(value = "/mobile", params = "mobile")
    @ResponseBody
    public LoginAppUser findByMobile(String mobile) {
        return userService.findByMobile(mobile);
    }

    /**
     * 根据OpenId查询用户信息
     *
     * @param openId openId
     */
    @GetMapping(value = "/openId", params = "openId")
    public LoginAppUser findByOpenId(String openId) {
        return userService.findByOpenId(openId);
    }




    @GetMapping("/menus/{roleCodes}")
    public List<SysMenu> findMenuByRoles(@PathVariable String roleCodes) {
        List<SysMenu> result = null;
        if (StringUtils.isNotEmpty(roleCodes)) {
            Set<String> roleSet = (Set<String>) Convert.toCollection(HashSet.class, String.class, roleCodes);
            result = menuService.findByRoleCodes(roleSet, CommonConstant.PERMISSION);
        }
        return result;
    }

    @GetMapping("/getUser")
    public SysUserDto getUser() {
        SysUser sysUser = sysUserDao.selectById("1");
        SysUserDto sysUserDto = new SysUserDto();
        BeanUtils.copyProperties(sysUser, sysUserDto);
        return sysUserDto;
    }

    @GetMapping(value = "/login1")
    public String findByUsername1() {
        String username = "admin";
        return JSON.toJSONString(userService.findByUsername(username));
    }
}
