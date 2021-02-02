package com.mic.security.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @Description:
 * @author: pf
 * @create: 2021/1/15 14:17
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "mic.security")
@RefreshScope
public class SecurityProperties {

    private AuthProperties auth = new AuthProperties();

    private PermitProperties ignore = new PermitProperties();

}
