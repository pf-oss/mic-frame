package com.mic.store;

import com.mic.constant.constant.SecurityConstants;
import com.mic.constant.util.BeanUtils;
import com.mic.properties.SecurityProperties;
import com.mic.utils.RedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import java.util.*;


/**
 * 优化自Spring Security的RedisTokenStore
 * 只用做测试使用
 * @author: pf
 * @create: 2021/1/18 17:01
 */
@Slf4j
public class CustomRedisTokenStoreJson implements TokenStore {

    private static final String ACCESS = "access:";
    private static final String AUTH_TO_ACCESS = "auth_to_access:";
    private static final String REFRESH_AUTH = "refresh_auth:";
    private static final String ACCESS_TO_REFRESH = "access_to_refresh:";
    private static final String REFRESH = "refresh:";
    private static final String REFRESH_TO_ACCESS = "refresh_to_access:";

    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

    /**
     * 认证配置
     */
    private SecurityProperties securityProperties;

    @Autowired
    private RedisRepository redisRepository;

    public CustomRedisTokenStoreJson(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    private String getExtractKey(String key){
        return AUTH_TO_ACCESS + key;
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        String key = authenticationKeyGenerator.extractKey(authentication);
        String extractKey = getExtractKey(key);
        OAuth2AccessToken accessToken = (OAuth2AccessToken)redisRepository.get(extractKey);
        if (accessToken != null){
            OAuth2Authentication storedAuthentication = readAuthentication(accessToken.getValue());
            if ((storedAuthentication == null || !key.equals(authenticationKeyGenerator.extractKey(storedAuthentication)))) {
                storeAccessToken(accessToken, authentication);
            }
        }
        return accessToken;
    }


    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        OAuth2Authentication auth2Authentication = readAuthentication(token.getValue());
        //是否开启token续签
        boolean isRenew = securityProperties.getAuth().getRenew().getEnable();
        if (isRenew && auth2Authentication != null) {
            OAuth2Request clientAuth = auth2Authentication.getOAuth2Request();
            //判断当前应用是否需要自动续签
            if (checkRenewClientId(clientAuth.getClientId())) {
                //获取过期时长
                int validitySeconds = getAccessTokenValiditySeconds(clientAuth.getClientId());
                if (validitySeconds > 0) {
                    double expiresRatio = token.getExpiresIn() / (double)validitySeconds;
                    //判断是否需要续签，当前剩余时间小于过期时长的50%则续签
                    double timeRatio = securityProperties.getAuth().getRenew().getTimeRatio();
                    if (expiresRatio <= timeRatio) {
                        //更新AccessToken过期时间
                        DefaultOAuth2AccessToken oAuth2AccessToken = (DefaultOAuth2AccessToken) token;
                        oAuth2AccessToken.setExpiration(new Date(System.currentTimeMillis() + (validitySeconds * 1000L)));
                        storeAccessToken(oAuth2AccessToken, auth2Authentication, true);
                    }
                }
            }
        }
        return auth2Authentication;
    }

    /**
     * 判断应用自动续签是否满足白名单和黑名单的过滤逻辑
     * @param clientId 应用id
     * @return 是否满足
     */
    private boolean checkRenewClientId(String clientId) {
        boolean result = true;
        //黑名单
        List<String> exclusiveClientIds = securityProperties.getAuth().getRenew().getExclusiveClientIds();

        //白名单
        List<String> includeClientIds = securityProperties.getAuth().getRenew().getIncludeClientIds();
        if (includeClientIds.size() > 0) {
            result = includeClientIds.contains(clientId);
        } else if(exclusiveClientIds.size() > 0) {
            result = !exclusiveClientIds.contains(clientId);
        }
        return result;
    }

    private String getClientIdStr(String clientId){
       return SecurityConstants.CACHE_CLIENT_KEY + ":" + clientId;
    }


    /**
     * 获取token的总有效时长
     * @param clientId 应用id
     */
    private int getAccessTokenValiditySeconds(String clientId) {
        String clientIdStr = getClientIdStr(clientId);
        ClientDetails clientDetails = (ClientDetails)redisRepository.get(clientIdStr);
        if (clientDetails.getAccessTokenValiditySeconds() != null) {
            return clientDetails.getAccessTokenValiditySeconds();
        }
        //返回默认值
        return SecurityConstants.ACCESS_TOKEN_VALIDITY_SECONDS;
    }


    private String getAuthStr(String token){
        return SecurityConstants.REDIS_TOKEN_AUTH + token;
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        String authStr = getAuthStr(token);
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) redisRepository.get(authStr);
        return oAuth2Authentication;
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return readAuthenticationForRefreshToken(token.getValue());
    }


    private String getRefreshAuth(String token){
        return REFRESH_AUTH + token;
    }

    public OAuth2Authentication readAuthenticationForRefreshToken(String token) {
        String refreshAuth = getRefreshAuth(token);
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication)redisRepository.get(refreshAuth);
        return oAuth2Authentication;
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        storeAccessToken(token, authentication, false);
    }

    private String getAccessKey(String token){
        return ACCESS + token;
    }


    private String getAuthToAccessKey(OAuth2Authentication oAuth2Authentication){
        return AUTH_TO_ACCESS + authenticationKeyGenerator.extractKey(oAuth2Authentication);
    }

    private String getApprovalKeyStr(OAuth2Authentication authentication){
       return SecurityConstants.REDIS_UNAME_TO_ACCESS + getApprovalKey(authentication);
    }

    private String clientIdToAccess(OAuth2Authentication authentication){
        return SecurityConstants.REDIS_CLIENT_ID_TO_ACCESS + authentication.getOAuth2Request().getClientId();
    }


    private String getRefreshToAccessKey(String token){
        return REFRESH_TO_ACCESS + token;
    }

    private String getAccessToRefreshKey(String token){
        return ACCESS_TO_REFRESH + token;
    }

    /**
     * 存储token
     * @param isRenew 是否续签
     */
    private void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication, boolean isRenew) {
        String accessKey = getAccessKey(token.getValue());
        String authKey = getAuthStr(token.getValue());
        String authToAccessKey = getAuthToAccessKey(authentication);
        String approvalKey = getApprovalKeyStr(authentication);
        String clientId = clientIdToAccess(authentication);

        OAuth2AccessToken oAuth2AccessToken = (OAuth2AccessToken) redisRepository.get(accessKey);
        //如果token已存在，并且不是续签的话直接返回
        if (!isRenew && oAuth2AccessToken != null) {
            return;
        }
        redisRepository.set(accessKey, token);
        redisRepository.set(authKey, authentication);
        redisRepository.set(authToAccessKey, token);

        //如果是续签token，需要先删除集合里旧的值
        if (oAuth2AccessToken != null) {
            if (!authentication.isClientOnly()) {
                redisRepository.listLrem(approvalKey, 1, oAuth2AccessToken);
            }
            redisRepository.listLrem(clientId, 1, oAuth2AccessToken);
        }
        if (!authentication.isClientOnly()) {
            redisRepository.rPush(approvalKey, token);
        }
        redisRepository.rPush(clientId, token);
        int seconds = token.getExpiresIn();
        if (seconds!= 0) {
            redisRepository.expire(authKey, seconds);
            redisRepository.expire(accessKey, seconds);
            redisRepository.expire(clientId, seconds);
            redisRepository.expire(authToAccessKey, seconds);
            redisRepository.expire(approvalKey, seconds);
        }

        OAuth2RefreshToken refreshToken = token.getRefreshToken();
        if (refreshToken != null && refreshToken.getValue() != null) {
            String refresh = token.getRefreshToken().getValue();
            String auth = token.getValue();
            String refreshToAccessKey = getRefreshToAccessKey(refreshToken.getValue());
            String accessToRefreshKey = getAccessToRefreshKey(token.getValue());
            redisRepository.set(refreshToAccessKey, auth);
            redisRepository.set(accessToRefreshKey, refresh);
            expireRefreshToken(refreshToken, refreshToAccessKey, accessToRefreshKey);

        }
    }


    private static String getApprovalKey(OAuth2Authentication authentication) {
        String userName = authentication.getUserAuthentication() == null ? ""
                : authentication.getUserAuthentication().getName();
        return getApprovalKey(authentication.getOAuth2Request().getClientId(), userName);
    }

    private static String getApprovalKey(String clientId, String userName) {
        return clientId + (userName == null ? "" : ":" + userName);
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken accessToken) {
        removeAccessToken(accessToken.getValue());
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        String key = getAccessKey(tokenValue);
        OAuth2AccessToken oAuth2AccessToken = (OAuth2AccessToken) redisRepository.get(key);
        return oAuth2AccessToken;
    }

    public void removeAccessToken(String tokenValue) {

        String accessKey = getAccessKey(tokenValue);
        String authKey = getAuthStr(tokenValue);
        String accessToRefreshKey = getAccessToRefreshKey(tokenValue);
        OAuth2AccessToken oAuth2AccessToken = (OAuth2AccessToken) redisRepository.get(accessKey);
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication)redisRepository.get(authKey);
        redisRepository.del(accessKey);
        redisRepository.del(accessToRefreshKey);
        redisRepository.del(authKey);

        if (oAuth2Authentication != null) {
            String key = authenticationKeyGenerator.extractKey(oAuth2Authentication);
            String authToAccessKey = getAuthToAccessKey(oAuth2Authentication);
            String unameKey = getApprovalKeyStr(oAuth2Authentication);
            String clientId =clientIdToAccess(oAuth2Authentication);

            redisRepository.del(authToAccessKey);
            redisRepository.listLrem(unameKey, 1, oAuth2AccessToken);
            redisRepository.listLrem(clientId, 1, oAuth2AccessToken);
            redisRepository.del(getAccessKey(key));
        }
    }



    private String getRefreshKey(String value){
        return REFRESH + value;
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        String refreshKey = getRefreshKey(refreshToken.getValue());
        String refreshAuthKey = getRefreshAuth(refreshToken.getValue());
        redisRepository.set(refreshKey, refreshToken);
        redisRepository.set(refreshAuthKey, authentication);
        expireRefreshToken(refreshToken,refreshKey, refreshAuthKey);
    }



    private void expireRefreshToken(OAuth2RefreshToken refreshToken, String refreshKey, String refreshAuthKey) {
        if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
            ExpiringOAuth2RefreshToken expiringRefreshToken = (ExpiringOAuth2RefreshToken) refreshToken;
            Date expiration = expiringRefreshToken.getExpiration();
            if (expiration != null) {
                int seconds = Long.valueOf((expiration.getTime() - System.currentTimeMillis()) / 1000L)
                        .intValue();
                redisRepository.expire(refreshAuthKey, seconds);
                redisRepository.expire(refreshKey, seconds);
            }
        }
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        String key = getRefreshKey(tokenValue);
        OAuth2RefreshToken oAuth2RefreshToken = (OAuth2RefreshToken) redisRepository.get(key);
        return oAuth2RefreshToken;
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken refreshToken) {
        removeRefreshToken(refreshToken.getValue());
    }

    public void removeRefreshToken(String tokenValue) {
        String refreshKey = getRefreshKey(tokenValue);
        String refreshAuthKey = getRefreshAuth(tokenValue);
        String refresh2AccessKey = getRefreshToAccessKey(tokenValue);
        String access2RefreshKey = getAccessToRefreshKey(tokenValue);
        redisRepository.del(refreshKey);
        redisRepository.del(refreshAuthKey);
        redisRepository.del(refresh2AccessKey);
        redisRepository.del(access2RefreshKey);
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        removeAccessTokenUsingRefreshToken(refreshToken.getValue());
    }

    private void removeAccessTokenUsingRefreshToken(String refreshToken) {
        String key = getRefreshToAccessKey(refreshToken);
        String accessToken = (String) redisRepository.get(key);
        redisRepository.del(accessToken);
        if (accessToken != null) {
            removeAccessToken(accessToken);
        }
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        String approvalKey = SecurityConstants.REDIS_UNAME_TO_ACCESS + getApprovalKey(clientId, userName);
        List<Object> objects = redisRepository.getList(approvalKey, 0, -1);
        List<OAuth2AccessToken> oAuth2AccessTokens = BeanUtils.batchTransform(OAuth2AccessToken.class, objects);
        return Collections.unmodifiableCollection(oAuth2AccessTokens);

    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        String key = SecurityConstants.REDIS_CLIENT_ID_TO_ACCESS + clientId;
        List<Object> objects = redisRepository.getList(key, 0, -1);
        List<OAuth2AccessToken> oAuth2AccessTokens = BeanUtils.batchTransform(OAuth2AccessToken.class, objects);
        return Collections.unmodifiableCollection(oAuth2AccessTokens);
    }

}
