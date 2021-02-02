package com.mic.user.center.tenant;

import com.mic.security.token.TenantUsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Description:  增加租户id，解决不同租户单点登录时角色没变化
 * @author: pf
 * @create: 2021/1/14 11:45
 */
public class TenantAuthenticationProvider extends DaoAuthenticationProvider {
    @Override
    protected Authentication createSuccessAuthentication(Object principal,
                                                         Authentication authentication, UserDetails user) {
        TenantUsernamePasswordAuthenticationToken authenticationToken = (TenantUsernamePasswordAuthenticationToken) authentication;
        TenantUsernamePasswordAuthenticationToken result = new TenantUsernamePasswordAuthenticationToken(
                principal, authentication.getCredentials(), user.getAuthorities(), authenticationToken.getClientId());
        result.setDetails(authenticationToken.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TenantUsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
