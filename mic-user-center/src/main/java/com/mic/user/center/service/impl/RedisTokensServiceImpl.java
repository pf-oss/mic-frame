package com.mic.user.center.service.impl;

import cn.hutool.core.util.PageUtil;
import com.mic.base.constant.SecurityConstants;
import com.mic.base.response.model.PageResult;
import com.mic.user.center.model.TokenVo;
import com.mic.user.center.service.ITokensService;
import com.mic.redis.utils.RedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * token管理服务(redis token)
 * @author: pf
 * @create: 2021/1/14 13:41
 */
@Slf4j
@Service
public class RedisTokensServiceImpl implements ITokensService {

    @Autowired
    private RedisRepository redisRepository;

    @Override
    public PageResult<TokenVo> listTokens(Map<String, Object> params, String clientId) {
        Integer page = MapUtils.getInteger(params, "page");
        Integer limit = MapUtils.getInteger(params, "limit");
        int[] startEnds = PageUtil.transToStartEnd(page, limit);
        //根据请求参数生成redis的key
        String redisKey = getRedisKey(params, clientId);
        long size = redisRepository.length(redisKey);
        List<TokenVo> result = new ArrayList<>(limit);
        RedisSerializer<Object> valueSerializer = RedisSerializer.java();
        //查询token集合
        List<Object> tokenObjs = redisRepository.getList(redisKey, startEnds[0], startEnds[1]-1);
        if (tokenObjs != null) {
            for (Object obj : tokenObjs) {
                DefaultOAuth2AccessToken accessToken = (DefaultOAuth2AccessToken)obj;
                //构造token对象
                TokenVo tokenVo = new TokenVo();
                tokenVo.setTokenValue(accessToken.getValue());
                tokenVo.setExpiration(accessToken.getExpiration());

                //获取用户信息
                Object authObj = redisRepository.get(SecurityConstants.REDIS_TOKEN_AUTH + accessToken.getValue());
                OAuth2Authentication authentication = (OAuth2Authentication)authObj;
                if (authentication != null) {
                    OAuth2Request request = authentication.getOAuth2Request();
                    tokenVo.setUsername(authentication.getName());
                    tokenVo.setClientId(request.getClientId());
                    tokenVo.setGrantType(request.getGrantType());
                }

                result.add(tokenVo);
            }
        }
        return PageResult.<TokenVo>builder().data(result).code(0).count(size).build();
    }

    /**
     * 根据请求参数生成redis的key
     */
    private String getRedisKey(Map<String, Object> params, String clientId) {
        String result;
        String username = MapUtils.getString(params, "username");
        if (StringUtils.isNotEmpty(username)) {
            result = SecurityConstants.REDIS_UNAME_TO_ACCESS + clientId + ":" + username;
        } else {
            result = SecurityConstants.REDIS_CLIENT_ID_TO_ACCESS + clientId;
        }
        return result;
    }
}
