package com.mic.user.api.service.fallback;


import com.mic.user.api.model.dto.SysUserDto;
import com.mic.user.api.model.vo.LoginAppUser;
import com.mic.user.api.model.vo.SysUserVo;
import com.mic.user.api.service.UserService;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: pf
 * @create: 2021/2/1 16:57
 */
@Component
public class UserServiceImpl implements UserService {

    @Override
    public SysUserDto getUser() {
        return null;
    }

    @Override
    public SysUserVo selectByUsername(String username) {
        return null;
    }

    @Override
    public LoginAppUser findByUsername(String username) {
        return null;
    }

    @Override
    public LoginAppUser findByMobile(String mobile) {
        return null;
    }

    @Override
    public LoginAppUser findByOpenId(String openId) {
        return null;
    }
}
