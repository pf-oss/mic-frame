package com.mic.service.impl;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mic.constant.response.model.PageResult;
import com.mic.model.IndexDto;
import com.mic.service.IIndexService;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Description:  索引
 * @author: pf
 * @create: 2021/1/20 16:39
 */
@Service
public class IndexServiceImpl implements IIndexService {
    private ObjectMapper mapper = new ObjectMapper();


    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    public IndexServiceImpl(ElasticsearchRestTemplate elasticsearchRestTemplate) {
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }

    @Override
    public boolean create(IndexDto indexDto) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexDto.getIndexName());
        request.settings(Settings.builder()
                .put("index.number_of_shards", indexDto.getNumberOfShards())
                .put("index.number_of_replicas", indexDto.getNumberOfReplicas())
        );
        if (StrUtil.isNotEmpty(indexDto.getMappingsSource())) {
            //mappings
            request.mapping(indexDto.getMappingsSource(), XContentType.JSON);
        }
        CreateIndexResponse response = restHighLevelClient
                .indices()
                .create(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    @Override
    public boolean delete(String indexName) throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        AcknowledgedResponse response = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    @Override
    public PageResult<Map<String, String>> list(String queryStr, String indices) throws IOException {
        if (StrUtil.isNotEmpty(queryStr)) {
            indices = queryStr;
        }
        Response response = restHighLevelClient.getLowLevelClient()
                .performRequest(new Request(
                        "GET",
                        "/_cat/indices?h=health,status,index,docsCount,docsDeleted,storeSize&s=cds:desc&format=json&index="+ StrUtil.nullToEmpty(indices)
                ));

        List<Map<String, String>> listOfIndicesFromEs = null;
        if (response != null) {
            String rawBody = EntityUtils.toString(response.getEntity());
            TypeReference<List<Map<String, String>>> typeRef = new TypeReference<List<Map<String, String>>>() {};
            listOfIndicesFromEs = mapper.readValue(rawBody, typeRef);
        }
        return PageResult.<Map<String, String>>builder().data(listOfIndicesFromEs).code(0).build();
    }

    @Override
    public Map<String, Object> show(String indexName) throws IOException {
        GetIndexRequest request = new GetIndexRequest(indexName);
        GetIndexResponse getIndexResponse = restHighLevelClient
                .indices().get(request, RequestOptions.DEFAULT);
        MappingMetaData mappingMetaData = getIndexResponse.getMappings().get(indexName);
        Map<String, Object> mappOpenMap = mappingMetaData.getSourceAsMap();
        List<AliasMetaData> aliasMetaData = getIndexResponse.getAliases().get(indexName);

        String settingsStr = getIndexResponse.getSettings().get(indexName).toString();
//        Object settingsObj = null;
//        if (StrUtil.isNotEmpty(settingsStr)) {
//            settingsObj = JsonUtil.parse(settingsStr);
//        }
        Map<String, Object> result = new HashMap<>(1);
        Map<String, Object> indexMap = new HashMap<>(3);
        List<String> aliasesList = new ArrayList<>(aliasMetaData.size());
        indexMap.put("aliases", aliasesList);
        indexMap.put("settings", settingsStr);
        indexMap.put("mappings", mappOpenMap);
        result.put(indexName, indexMap);
        //获取aliases数据
        for (AliasMetaData aliases : aliasMetaData) {
            aliasesList.add(aliases.getAlias());
        }
        return result;
    }
}
