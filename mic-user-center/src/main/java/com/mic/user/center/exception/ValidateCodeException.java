package com.mic.user.center.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Description: 验证码异常
 * @author: pf
 * @create: 2021/1/14 11:28
 */
public class ValidateCodeException extends AuthenticationException {
	private static final long serialVersionUID = -7285211528095468156L;

	public ValidateCodeException(String msg) {
		super(msg);
	}
}
