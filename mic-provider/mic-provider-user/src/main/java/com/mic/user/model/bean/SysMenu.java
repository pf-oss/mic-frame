package com.mic.user.model.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;



/**
 * @Description:
 * @author: pf
 * @create: 2021/1/12 15:47
 */
@Data
@TableName("sys_menu")
public class SysMenu implements Serializable {

    private Integer id;
    
    private Integer parentId;
    
    private String name;
    
    private String url;
    
    private String path;
    
    private String pathMethod;
    
    private String css;
    
    private Integer sort;
    
    private Date createTime;
    
    private Date updateTime;
    
    private Integer type;
    
    private Integer hidden;
    
    /**
     * 租户字段
     */
     private String tenantId;
    

}