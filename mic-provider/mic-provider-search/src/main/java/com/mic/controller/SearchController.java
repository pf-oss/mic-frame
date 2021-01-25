package com.mic.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.mic.constant.response.model.PageResult;
import com.mic.model.SearchDto;
import com.mic.service.ISearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;


/**
 * @Description:  通用搜索
 * @author: pf
 * @create: 2021/1/20 17:22
 */
@Slf4j
@RestController
@RequestMapping("/search")
public class SearchController {
    private final ISearchService searchService;

    public SearchController(ISearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * 查询文档列表
     * @param indexName 索引名
     * @param searchDto 搜索Dto
     */
    @PostMapping("/{indexName}")
    public PageResult<JsonNode> strQuery(@PathVariable String indexName, @RequestBody(required = false) SearchDto searchDto) throws IOException {
        if (searchDto == null) {
            searchDto = new SearchDto();
        }
        return searchService.strQuery(indexName, searchDto);
    }
}