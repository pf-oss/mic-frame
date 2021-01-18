package com.mic.model;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @author: pf
 * @create: 2021/1/14 11:48
 */
@Setter
@Getter
public class TokenVo implements Serializable {
   /**
    * token的值
    */
   private String tokenValue;
   /**
    * 到期时间
    */
   private Date expiration;
   /**
    * 用户名
    */
   private String username;
   /**
    * 所属应用
    */
   private String clientId;
   /**
    * 授权类型
    */
   private String grantType;
}
