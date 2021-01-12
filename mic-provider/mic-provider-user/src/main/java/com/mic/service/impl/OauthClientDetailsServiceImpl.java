package com.mic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mic.dao.OauthClientDetailsDao;
import com.mic.model.bean.OauthClientDetails;
import com.mic.service.OauthClientDetailsService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @Description:
 * @author: pf
 * @create: 2021/1/12 15:20
 */
@Service("oauthClientDetailsService")
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsDao, OauthClientDetails> implements OauthClientDetailsService {

    @Resource
    private OauthClientDetailsDao oauthClientDetailsDao;

}