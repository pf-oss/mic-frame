package com.mic;

import com.alibaba.fastjson.JSON;

import com.mic.user.dao.SysUserDao;
import com.mic.user.model.bean.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class MicProviderUserApplicationTests {


    @Autowired
    private SysUserDao sysUserDao;

    @Test
    void contextLoads() {
        log.info("输出数据: " + JSON.toJSONString(sysUserDao.selectById(1)));
    }

    @Test
    void insert(){
        SysUser sysUser = new SysUser();
        sysUser.setCompany("akldjflkjflkjflk");
        sysUser.setNickname("张三");
        sysUser.setUsername("lisi");
        sysUser.setPassword("456");
        sysUser.setType("fjskdljfk");
        sysUserDao.insert(sysUser);
    }


    @Test
    void del(){
        sysUserDao.deleteById(19);
    }


    @Test
    void update(){
        SysUser sysUser = new SysUser();
        sysUser.setId(19L);
        sysUserDao.updateById(sysUser);

    }
}
