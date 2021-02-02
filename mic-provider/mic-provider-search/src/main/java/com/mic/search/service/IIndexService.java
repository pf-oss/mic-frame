package com.mic.search.service;


import com.mic.base.response.model.PageResult;
import com.mic.search.model.IndexDto;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;
import java.util.Map;

/**
 * @Description:  索引
 * @author: pf
 * @create: 2021/1/20 16:35
 */
public interface IIndexService {
    /**
     * 创建索引
     */
    boolean create(IndexDto indexDto) throws IOException;

    /**
     * 删除索引
     * @param indexName 索引名
     */
    boolean delete(String indexName) throws IOException;

    /**
     * 索引列表
     * @param queryStr 搜索字符串
     * @param indices 默认显示的索引名
     */
    PageResult<Map<String, String>> list(String queryStr, String indices) throws IOException;

    /**
     * 显示索引明细
     * @param indexName 索引名
     */
    Map<String, Object> show(String indexName) throws IOException;
}
