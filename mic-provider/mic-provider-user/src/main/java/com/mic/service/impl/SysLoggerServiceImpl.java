package com.mic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mic.dao.SysLoggerDao;
import com.mic.model.bean.SysLogger;
import com.mic.service.SysLoggerService;
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