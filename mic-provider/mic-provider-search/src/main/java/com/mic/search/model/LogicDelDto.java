package com.mic.search.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * @Description:  逻辑删除条件对象
 * @author: pf
 * @create: 2021/1/20 17:21
 */
@Setter
@Getter
@AllArgsConstructor
public class LogicDelDto {
    /**
     * 逻辑删除字段名
     */
    private String logicDelField;
    /**
     * 逻辑删除字段未删除的值
     */
    private String logicNotDelValue;
}
