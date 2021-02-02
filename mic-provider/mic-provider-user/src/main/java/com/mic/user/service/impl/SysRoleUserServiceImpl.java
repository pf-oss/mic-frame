package com.mic.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mic.user.model.bean.SysRoleUser;
import com.mic.user.service.SysRoleUserService;
import com.mic.user.dao.SysRoleUserDao;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @Description:
 * @author: pf
 * @create: 2021/1/12 15:15
 */
@Service("sysRoleUserService")
public class SysRoleUserServiceImpl extends ServiceImpl<SysRoleUserDao, SysRoleUser> implements SysRoleUserService {

    @Resource
    private SysRoleUserDao sysRoleUserDao;

}