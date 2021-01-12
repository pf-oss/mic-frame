package com.mic.service;

import com.mic.constant.ServiceNameConstants;
import com.mic.model.dto.SysUserDto;

import com.mic.service.fallback.UserServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * @Description:
 * @author: pf
 * @create: 2021/1/12 17:15
 */
@FeignClient(name = ServiceNameConstants.MIC_PROVIDER_USER, fallbackFactory = UserServiceFallbackFactory.class, decode404 = true)
public interface UserService {

    /**
     * @Description:
     * @return: com.mic.model.dto.SysUserDto
     */
    @GetMapping(value = "/test/getUser")
    SysUserDto getUser();

}
