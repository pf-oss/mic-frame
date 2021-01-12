package com.mic.model.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * @Description:
 * @author: pf
 * @create: 2021/1/12 15:45
 */
@TableName("sys_user")
@Data
public class SysUser implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private String username;
    
    private String password;
    
    private String nickname;
    
    private String headImgUrl;
    
    private String mobile;
    
    private Integer sex;
    
    private Integer enabled;
    
    private String type;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;
    
    private String company;
    
    private String openId;

    @TableLogic
    private Integer isDel;
    

}