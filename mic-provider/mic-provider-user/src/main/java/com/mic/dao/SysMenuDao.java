package com.mic.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mic.model.bean.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


/**
 * @Description:
 * @author: pf
 * @create: 2021/1/12 15:10
 */
@Repository
public interface SysMenuDao extends BaseMapper<SysMenu> {


    List<SysMenu> findMenusByRoleCodes(@Param("roleCodes") Set<String> roleCodes, @Param("type") Integer type);

}