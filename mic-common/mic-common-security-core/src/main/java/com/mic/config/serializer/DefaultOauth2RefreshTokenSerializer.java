package com.mic.config.serializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;

import java.lang.reflect.Type;


/**
 * @Description:  自定义默认的刷新token序列化工具类
 * @author: pf
 * @create: 2021/1/18 18:16
 */
public class DefaultOauth2RefreshTokenSerializer implements ObjectDeserializer {

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        if (type == DefaultOAuth2RefreshToken.class) {
            JSONObject jsonObject = parser.parseObject();
            String tokenId = jsonObject.getString("value");
            DefaultOAuth2RefreshToken refreshToken = new DefaultOAuth2RefreshToken(tokenId);
            return (T) refreshToken;
        }
        return null;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
