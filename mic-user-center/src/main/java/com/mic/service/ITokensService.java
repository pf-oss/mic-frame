package com.mic.service;

import com.mic.constant.response.model.PageResult;
import com.mic.model.TokenVo;
import java.util.Map;


/**
 * @Description:
 * @author: pf
 * @create: 2021/1/14 11:49
 */
public interface ITokensService {
    /**
     * 查询token列表
     * @param params 请求参数
     * @param clientId 应用id
     */
    PageResult<TokenVo> listTokens(Map<String, Object> params, String clientId);
}
