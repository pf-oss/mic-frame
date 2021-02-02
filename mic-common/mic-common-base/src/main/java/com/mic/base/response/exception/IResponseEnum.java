package com.mic.base.response.exception;


 /**
  * @Description:  异常返回码枚举接口
  * @author: pf
  * @create: 2021/1/20 10:46
  */
public interface IResponseEnum {
    /**
     * 获取返回码
     * @return 返回码
     */
    int getCode();

    /**
     * 获取返回信息
     * @return 返回信息
     */
    String getMsg();
}
