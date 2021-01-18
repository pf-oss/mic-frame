package com.mic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mic.dao.SysMenuDao;
import com.mic.model.bean.SysMenu;
import com.mic.service.SysMenuService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @Description:
 * @author: pf
 * @create: 2021/1/12 15:18
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenu> implements SysMenuService {

    @Resource
    private SysMenuDao sysMenuDao;


    @Override
    public List<SysMenu> findByRoleCodes(Set<String> roleCodes, Integer type) {
        return sysMenuDao.findMenusByRoleCodes(roleCodes, type);
    }

}