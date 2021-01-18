package com.mic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * @Description:
 * @author: pf
 * @create: 2021/1/14 15:54
 */
@Configuration
public class DefaultPasswordConfig {
	/**
	 * 装配BCryptPasswordEncoder用户密码的匹配
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder()	{
		return new BCryptPasswordEncoder();
	}
}
