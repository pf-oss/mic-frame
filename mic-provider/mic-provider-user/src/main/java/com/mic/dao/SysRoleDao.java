package com.mic.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mic.model.bean.SysRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Description:
 * @author: pf
 * @create: 2021/1/12 15:11
 */
@Repository
public interface SysRoleDao extends BaseMapper<SysRole> {


    List<SysRole> findRolesByUserId(@Param("userId") Long userId);

}