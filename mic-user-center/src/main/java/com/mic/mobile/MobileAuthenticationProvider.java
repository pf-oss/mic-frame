package com.mic.mobile;


import com.mic.service.MicUserDetailsService;
import com.mic.token.MobileAuthenticationToken;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * @Description:
 * @author: pf
 * @create: 2021/1/14 11:42
 */
@Setter
public class MobileAuthenticationProvider implements AuthenticationProvider {
    private MicUserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) {
        MobileAuthenticationToken authenticationToken = (MobileAuthenticationToken) authentication;
        String mobile = (String) authenticationToken.getPrincipal();
        String password = (String) authenticationToken.getCredentials();
        UserDetails user = userDetailsService.loadUserByMobile(mobile);
        if (user == null) {
            throw new InternalAuthenticationServiceException("手机号或密码错误");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("手机号或密码错误");
        }
        MobileAuthenticationToken authenticationResult = new MobileAuthenticationToken(user, password, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
