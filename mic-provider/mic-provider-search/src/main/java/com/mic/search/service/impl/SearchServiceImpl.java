package com.mic.search.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.mic.base.response.model.PageResult;
import com.mic.search.model.SearchDto;
import com.mic.search.service.ISearchService;
import com.mic.utils.SearchBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;
import java.io.IOException;

/**
 * @Description: 通用搜索
 * @author: pf
 * @create: 2021/1/20 16:39
 */
@Service
public class SearchServiceImpl implements ISearchService {
//    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
//
//    public SearchServiceImpl(ElasticsearchRestTemplate elasticsearchRestTemplate) {
//        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
//    }

    /**
     * StringQuery通用搜索
     * @param indexName 索引名
     * @param searchDto 搜索Dto
     * @return
     */
    @Override
    public PageResult<JsonNode> strQuery(String indexName, SearchDto searchDto) throws IOException {
        return SearchBuilder.builder(indexName)
                .setStringQuery(searchDto.getQueryStr())
                .addSort(searchDto.getSortCol(), SortOrder.DESC)
                .setIsHighlight(searchDto.getIsHighlighter())
                .getPage(searchDto.getPage(), searchDto.getLimit());
    }
}
