package com.mic.store;

import com.mic.properties.SecurityProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 *  认证服务器使用Redis存取令牌
 *  注意: 需要配置redis参数
 * @author: pf
 * @create: 2021/1/14 11:22
 */
@ConditionalOnProperty(prefix = "mic.oauth2.token.store", name = "type", havingValue = "redisAndJson", matchIfMissing = true)
public class AuthRedisTokenStoreJson {

    @Bean
    public TokenStore tokenStore(SecurityProperties securityProperties) {
        return new CustomRedisTokenStoreJson(securityProperties);
    }
}
