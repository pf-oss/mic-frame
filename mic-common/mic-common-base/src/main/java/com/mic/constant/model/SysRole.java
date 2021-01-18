package com.mic.constant.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;


/**
 * @Description:  角色
 * @author: pf
 * @create: 2021/1/14 10:29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRole implements Serializable {

    private Long id;
    private String code;
    private String name;
    private Long userId;
    private Date createTime;
    private Date updateTime;
}
