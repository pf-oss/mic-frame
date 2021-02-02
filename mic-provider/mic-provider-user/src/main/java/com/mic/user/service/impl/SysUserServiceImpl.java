package com.mic.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mic.base.constant.CommonConstant;
import com.mic.base.util.BeanUtils;
import com.mic.user.model.bean.SysMenu;
import com.mic.user.model.bean.SysRole;
import com.mic.user.model.bean.SysUser;
import com.mic.user.dao.SysRoleDao;
import com.mic.user.dao.SysRoleMenuDao;
import com.mic.user.dao.SysUserDao;
import com.mic.user.api.model.vo.LoginAppUser;
import com.mic.user.api.model.vo.SysRoleVo;
import com.mic.user.api.model.vo.SysUserVo;
import com.mic.user.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @Description:
 * @author: pf
 * @create: 2021/1/12 15:14
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {
    @Resource
    private SysUserDao sysUserDao;

    @Resource
    private SysRoleDao sysRoleDao;

    @Resource
    private SysRoleMenuDao sysRoleMenuDao;

    @Override
    public SysUserVo selectByUsername(String username) {
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserLambdaQueryWrapper.eq(SysUser::getUsername, username);
        SysUser sysUser = sysUserDao.selectOne(sysUserLambdaQueryWrapper);
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtils.copyProperties(sysUser, sysUserVo);
        return sysUserVo;
    }

    @Override
    public LoginAppUser findByUsername(String username) {
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserLambdaQueryWrapper.eq(SysUser::getUsername, username);
        SysUser sysUser = sysUserDao.selectOne(sysUserLambdaQueryWrapper);
        return getLoginAppUser(sysUser);
    }

    @Override
    public LoginAppUser findByOpenId(String openId) {
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserLambdaQueryWrapper.eq(SysUser::getOpenId, openId);
        SysUser sysUser = sysUserDao.selectOne(sysUserLambdaQueryWrapper);
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtils.copyProperties(sysUser, sysUserVo);
        return getLoginAppUser(sysUser);
    }

    @Override
    public LoginAppUser findByMobile(String mobile) {
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserLambdaQueryWrapper.eq(SysUser::getMobile, mobile);
        SysUser sysUser = sysUserDao.selectOne(sysUserLambdaQueryWrapper);
        return getLoginAppUser(sysUser);
    }

    private LoginAppUser getLoginAppUser(SysUser sysUser) {
        if (sysUser != null) {
      //      LoginAppUser loginAppUser = new LoginAppUser();
            LoginAppUser loginAppUser = new LoginAppUser();
            BeanUtils.copyProperties(sysUser, loginAppUser);

            List<SysRole> sysRoles = sysRoleDao.findRolesByUserId(sysUser.getId());
            List<SysRoleVo> sysRoleVos = BeanUtils.batchTransform(SysRoleVo.class, sysRoles);
            // 设置角色
            loginAppUser.setRoles(sysRoleVos);

            if (!CollectionUtils.isEmpty(sysRoleVos)) {
                Set<Long> roleIds = sysRoleVos.parallelStream().map(SysRoleVo::getId).collect(Collectors.toSet());
                List<SysMenu> menus = sysRoleMenuDao.findMenusByRoleIds(roleIds, CommonConstant.PERMISSION);
                if (!CollectionUtils.isEmpty(menus)) {
                    Set<String> permissions = menus.parallelStream().map(SysMenu::getPath).collect(Collectors.toSet());
                    // 设置权限集合
                    loginAppUser.setPermissions(permissions);
                }
            }
            return loginAppUser;
        }
        return null;
    }
}