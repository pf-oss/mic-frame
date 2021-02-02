package com.mic.gateway.feign;

import com.mic.gateway.feign.fallback.MenuServiceFallbackFactory;
import com.mic.base.constant.ServiceNameConstants;
import com.mic.base.model.SysMenu;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;


/**
 * @Description:
 * @author: pf
 * @create: 2021/1/15 13:53
 */
@FeignClient(name = ServiceNameConstants.USER_SERVICE, fallbackFactory = MenuServiceFallbackFactory.class, decode404 = true)
public interface MenuService {
	/**
	 * 角色菜单列表
	 * @param roleCodes
	 */
    @GetMapping("/user/menus/{roleCodes}")
    List<SysMenu> findMenuByRoles(@PathVariable("roleCodes") String roleCodes);
}
