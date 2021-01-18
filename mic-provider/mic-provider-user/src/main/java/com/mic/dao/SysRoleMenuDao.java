package com.mic.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mic.model.bean.SysMenu;
import com.mic.model.bean.SysRoleMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


/**
 * @Description:
 * @author: pf
 * @create: 2021/1/12 15:11
 */
@Repository
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenu> {


    List<SysMenu> findMenusByRoleIds(@Param("roleIds") Set<Long> roleIds, @Param("type") Integer type);

}