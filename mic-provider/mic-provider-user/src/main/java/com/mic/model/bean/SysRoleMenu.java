package com.mic.model.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * @Description:
 * @author: pf
 * @create: 2021/1/12 15:46
 */
@Data
@TableName("sys_role_menu")
public class SysRoleMenu implements Serializable {

    private Integer roleId;
    
    private Integer menuId;

}