package com.mic.controller;

import com.mic.constant.response.model.PageResult;
import com.mic.constant.response.model.Result;
import com.mic.model.IndexDto;
import com.mic.properties.IndexProperties;
import com.mic.service.IIndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * 索引管理
 *
 * @author zlt
 */
@Slf4j
@RestController
@RequestMapping("/admin")
public class IndexController {
    @Autowired
    private IIndexService indexService;

    @Resource
    private IndexProperties indexProperties;

    @PostMapping("/index")
    public Result createIndex(@RequestBody IndexDto indexDto) throws IOException {
        if (indexDto.getNumberOfShards() == null) {
            indexDto.setNumberOfShards(1);
        }
        if (indexDto.getNumberOfReplicas() == null) {
            indexDto.setNumberOfReplicas(0);
        }
        indexService.create(indexDto);
        return Result.succeed("操作成功");
    }

    /**
     * 索引列表
     */
    @GetMapping("/indices")
    public PageResult<Map<String, String>> list(@RequestParam(required = false) String queryStr) throws IOException {
        return indexService.list(queryStr, indexProperties.getShow());
    }

    /**
     * 索引明细
     */
    @GetMapping("/index")
    public Result<Map<String, Object>> showIndex(String indexName) throws IOException {
        Map<String, Object> result = indexService.show(indexName);
        return Result.succeed(result);
    }

    /**
     * 删除索引
     */
    @DeleteMapping("/index")
    public Result deleteIndex(String indexName) throws IOException {
        indexService.delete(indexName);
        return Result.succeed("操作成功");
    }
}
