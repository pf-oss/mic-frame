package com.mic.config;

import com.mic.resolver.ClientArgumentResolver;
import com.mic.resolver.TokenArgumentResolver;
import com.mic.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import javax.annotation.Resource;
import java.util.List;


/**
 * @Description:
 * @author: pf
 * @create: 2021/1/14 15:57
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Lazy
    @Resource
    private UserService userService;

    /**
     * Token参数解析
     *
     * @param argumentResolvers 解析类
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //注入用户信息
        argumentResolvers.add(new TokenArgumentResolver(userService));
        //注入应用信息
        argumentResolvers.add(new ClientArgumentResolver());
    }
}
