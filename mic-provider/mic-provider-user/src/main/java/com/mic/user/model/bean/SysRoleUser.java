package com.mic.user.model.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @author: pf
 * @create: 2021/1/12 15:45
 */
@TableName("sys_role_user")
@Data
public class SysRoleUser implements Serializable {

    private Integer userId;
    
    private Integer roleId;
    
}