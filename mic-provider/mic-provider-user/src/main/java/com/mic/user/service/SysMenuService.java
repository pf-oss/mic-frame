package com.mic.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mic.user.model.bean.SysMenu;

import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @author: pf
 * @create: 2021/1/12 15:08
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 角色菜单列表
     * @param roleCodes
     * @return
     */
    List<SysMenu> findByRoleCodes(Set<String> roleCodes, Integer type);

}