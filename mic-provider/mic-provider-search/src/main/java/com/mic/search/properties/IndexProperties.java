package com.mic.search.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description:  索引配置
 * @author: pf
 * @create: 2021/1/20 16:34
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "mic.indices")
public class IndexProperties {
    /**
     * 配置过滤的索引名：默认只显示这些索引
     */
    private String show;
}
