package com.mic.granter;

import com.mic.token.MobileAuthenticationToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @Description: 手机号+ 密码模式
 * @author: pf
 * @create: 2021/1/14 11:38
 */
public class MobilePwdGranter extends AbstractTokenGranter {
    private static final String GRANT_TYPE = "mobile_password";

    private final AuthenticationManager authenticationManager;

    public MobilePwdGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices
            , ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
        String mobile = parameters.get("mobile");
        String password = parameters.get("password");
        // Protect from downstream leaks of password
        parameters.remove("password");

        Authentication userAuth = new MobileAuthenticationToken(mobile, password);
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
        userAuth = authenticationManager.authenticate(userAuth);
        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate mobile: " + mobile);
        }

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }
}