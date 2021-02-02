package com.mic.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mic.user.model.bean.SysLogger;
import com.mic.user.service.SysLoggerService;
import com.mic.user.dao.SysLoggerDao;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;


/**
 * @Description:
 * @author: pf
 * @create: 2021/1/12 15:18
 */
@Service("sysLoggerService")
public class SysLoggerServiceImpl extends ServiceImpl<SysLoggerDao, SysLogger> implements SysLoggerService {

    @Resource
    private SysLoggerDao sysLoggerDao;

}