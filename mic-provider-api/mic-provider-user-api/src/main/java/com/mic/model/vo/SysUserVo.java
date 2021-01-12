package com.mic.model.vo;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;


/**
 * @Description:
 * @author: pf
 * @create: 2021/1/12 15:45
 */
@Data
public class SysUserVo implements Serializable {

    private Integer id;
    
    private String username;
    
    private String password;
    
    private String nickname;
    
    private String headImgUrl;
    
    private String mobile;
    
    private Integer sex;
    
    private Integer enabled;
    
    private String type;

    private Date createTime;

    private Date updateTime;
    
    private String company;
    
    private String openId;

    private Integer isDel;
    

}