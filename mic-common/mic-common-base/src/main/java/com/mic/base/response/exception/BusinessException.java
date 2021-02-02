package com.mic.base.response.exception;


import com.mic.base.response.enums.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description:  业务异常
 * @author: pf
 * @create: 2021/1/19 14:10
 */
@Slf4j
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 3160241586346324994L;

	/**
	 * 异常码
	 */
	protected int code;

	public BusinessException() { }

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(int code, String message) {
		super(message);
		this.code = code;
	}

    public BusinessException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.msg());
        this.code = errorCodeEnum.code();
    }

	public BusinessException(int code, String msgFormat, Object... args) {
		super(String.format(msgFormat, args));
		this.code = code;
	}

	public BusinessException(ErrorCodeEnum codeEnum, Object... args) {
		super(String.format(codeEnum.msg(), args));
		this.code = codeEnum.code();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
