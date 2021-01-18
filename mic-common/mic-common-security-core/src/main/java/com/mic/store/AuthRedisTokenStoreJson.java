package com.mic.store;

import com.mic.config.serializer.FastjsonRedisTokenStoreSerializationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 *  认证服务器使用Redis存取令牌
 *  注意: 需要配置redis参数
 * @author: pf
 * @create: 2021/1/14 11:22
 */
@ConditionalOnProperty(prefix = "mic.oauth2.token.store", name = "type", havingValue = "redisAndJson", matchIfMissing = true)
public class AuthRedisTokenStoreJson {


    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisTokenStore redisTokenStore(){
        RedisTokenStore store = new RedisTokenStore(redisConnectionFactory);
        //自定义json进行序列化和反序列化
        store.setSerializationStrategy(new FastjsonRedisTokenStoreSerializationStrategy());
        return store;
    }
}
