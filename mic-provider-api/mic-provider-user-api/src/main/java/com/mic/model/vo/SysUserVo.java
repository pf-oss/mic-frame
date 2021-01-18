package com.mic.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @Description:
 * @author: pf
 * @create: 2021/1/12 15:45
 */
@Data
public class SysUserVo implements Serializable {

    private Long id;
    
    private String username;
    
    private String password;
    
    private String nickname;
    
    private String headImgUrl;
    
    private String mobile;
    
//    private Integer sex;
    
    private Boolean enabled;
    
    private String type;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    
    private String company;
    
    private String openId;

    private Integer isDel;

    private List<SysRoleVo> roles;
    

}