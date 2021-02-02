package com.mic.base.response.model;

/**
 * @Description:
 * @author: pf
 * @create: 2021/1/14 11:33
 */
public enum CodeEnum {
    SUCCESS(0),
    ERROR(1);

    private Integer code;
    CodeEnum(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
