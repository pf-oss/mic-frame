package com.mic.base.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * @Description:
 * @author: pf
 * @create: 2021/1/14 10:21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysMenu implements Serializable {

	private Long id;
	private Long parentId;
	private String name;
	private String css;
	private String url;
	private String path;
	private Integer sort;
	private Integer type;
	private Boolean hidden;
	/**
	 * 请求的类型
	 */
	private String pathMethod;
	private List<SysMenu> subMenus;
	private Long roleId;
	private Set<Long> menuIds;

	private Date createTime;

	private Date updateTime;
}
