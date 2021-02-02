package com.mic.user.model.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;



/**
 * @Description:
 * @author: pf
 * @create: 2021/1/12 15:47
 */
@TableName("sys_role_menu")
@Data
public class SysLogger implements Serializable {

    private Integer id;
    
    /**
     * 应用名
     */
     private String applicationName;
    
    /**
     * 类名
     */
     private String className;
    
    /**
     * 方法名
     */
     private String methodName;
    
    /**
     * 用户id
     */
     private Integer userId;
    
    /**
     * 用户名
     */
     private String userName;
    
    /**
     * 租户id
     */
     private String clientId;
    
    /**
     * 操作信息
     */
     private String operation;
    
    /**
     * 创建时间
     */
     private String timestamp;

}