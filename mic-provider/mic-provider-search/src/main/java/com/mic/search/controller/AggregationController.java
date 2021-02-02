package com.mic.search.controller;

import com.mic.search.service.IAggregationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @Description:  聚合统计
 * @author: pf
 * @create: 2021/1/20 17:24
 */
@Slf4j
@RestController
@RequestMapping("/agg")
public class AggregationController {

    @Resource
    private IAggregationService aggregationService;

//    public AggregationController(IAggregationService aggregationService) {
//        this.aggregationService = aggregationService;
//    }

    /**
     * 访问统计聚合查询
     * @param indexName 索引名
     * @param routing es的路由
     */
    @GetMapping("/requestStat/{indexName}/{routing}")
    public Map<String, Object> requestStatAgg(@PathVariable String indexName, @PathVariable String routing) throws IOException {
        return aggregationService.requestStatAgg(indexName, routing);
    }
}