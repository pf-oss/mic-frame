package com.mic.model.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;



/**
 * @Description:
 * @author: pf
 * @create: 2021/1/12 15:46
 */
@TableName("sys_role_menu")
@Data
public class SysRole implements Serializable {

    private Integer id;
    
    /**
     * 角色code
     */
     private String code;
    
    /**
     * 角色名
     */
     private String name;
    
    private Date createTime;
    
    private Date updateTime;
    
    /**
     * 租户字段
     */
     private String tenantId;

}