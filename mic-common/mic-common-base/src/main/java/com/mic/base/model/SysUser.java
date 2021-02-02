package com.mic.base.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @Description:
 * @author: pf
 * @create: 2021/1/14 10:28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUser implements Serializable {

	private Long id;
	private String username;
	private String password;
	private String nickname;
	private String headImgUrl;
	private String mobile;
	private Integer sex;
	private Boolean enabled;
	private String type;
	private String openId;
	private boolean isDel;

	private List<SysRole> roles;
	private String roleId;
	private String oldPassword;
	private String newPassword;

	private Date createTime;
	private Date updateTime;
}
