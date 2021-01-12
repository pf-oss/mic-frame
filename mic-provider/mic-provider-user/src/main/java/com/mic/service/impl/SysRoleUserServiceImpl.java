package com.mic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mic.dao.SysRoleUserDao;
import com.mic.model.bean.SysRoleUser;
import com.mic.service.SysRoleUserService;
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