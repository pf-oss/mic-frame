package com.mic.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mic.user.model.bean.SysRoleMenu;
import com.mic.user.dao.SysRoleMenuDao;
import com.mic.user.service.SysRoleMenuService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;


/**
 * @Description:
 * @author: pf
 * @create: 2021/1/12 15:16
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenu> implements SysRoleMenuService {

    @Resource
    private SysRoleMenuDao sysRoleMenuDao;


}