package com.mic.feign.fallback;

import cn.hutool.core.collection.CollectionUtil;
import com.mic.feign.MenuService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @Description:
 * @author: pf
 * @create: 2021/1/15 13:54
 */
@Slf4j
@Component
public class MenuServiceFallbackFactory implements FallbackFactory<MenuService> {
    @Override
    public MenuService create(Throwable throwable) {
        return roleIds -> {
            log.error("调用findByRoleCodes异常：{}", roleIds, throwable);
            return CollectionUtil.newArrayList();
        };
    }
}
