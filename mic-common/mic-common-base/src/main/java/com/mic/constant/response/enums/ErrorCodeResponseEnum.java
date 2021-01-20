package com.mic.constant.response.enums;

import com.mic.constant.response.exception.BaseExceptionInterface;
import com.mic.constant.response.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @Description:
 * @author: pf
 * @create: 2021/1/19 15:13
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeResponseEnum implements BaseExceptionInterface {
	/**
	 * Gl 99990100 error code enum.
	 */
	GL99990100(9999100, "参数异常"),
	/**
	 * Gl 99990401 error code enum.
	 */
	GL99990401(99990401, "无访问权限");

	private int code;

    private String msg;

    public void businessException(){
       throw new BusinessException(code, msg);
    }


}
