package com.mic.search.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.mic.base.response.model.PageResult;
import com.mic.search.model.SearchDto;
import java.io.IOException;

/**
 * @Description:
 * @author: pf
 * @create: 2021/1/20 16:35
 */
public interface ISearchService {


    /**
     * StringQuery通用搜索
     * @param indexName 索引名
     * @param searchDto 搜索Dto
     * @return
     */
    PageResult<JsonNode> strQuery(String indexName, SearchDto searchDto) throws IOException;
}
