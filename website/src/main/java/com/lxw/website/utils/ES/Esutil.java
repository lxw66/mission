package com.lxw.website.utils.ES;

import com.lxw.website.es.EsConfig;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author mission
 * @version 1.0
 * @description: TODO
 * @date 2021/8/11 16:27
 */
@Component
public class Esutil<T> {

    @Autowired()
    private RestHighLevelClient highLevelClient;

    /**
     * 判断索引是否存在
     * @param index
     * @return
     * @throws IOException
     */
    public boolean isIndexExit(String index) throws IOException {
        boolean isExit=false;
        GetIndexRequest getIndexRequest=new GetIndexRequest(index);
        isExit=highLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        return isExit;
    }

    public Object getIndexDate(String index) throws IOException {
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);
        //显示证实的数量
        searchSourceBuilder.trackTotalHits(true);
        //设置超时时间
        TimeValue timeValue=new TimeValue(60 ,TimeUnit.SECONDS);
        searchSourceBuilder.timeout(timeValue);
        //设置返回的字段
        String[] includes=new String[]{"createtime","text","username","age","userid","email","gender"};
        //设置排除字段
        ///String[] excludeFields = new String[] {"createtime"};
        searchSourceBuilder.fetchSource(includes, new String[]{});
        //match 匹配字段值
        MatchQueryBuilder matchQueryBuilder=new MatchQueryBuilder("username","样想念它我会怎样想念");
        //Range条件
        RangeQueryBuilder queryBuilder=new RangeQueryBuilder("userid");
        queryBuilder.gte(0); queryBuilder.lte(10000000);
        //添加条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(matchQueryBuilder);
        boolQueryBuilder.must(queryBuilder);

        searchSourceBuilder.query(boolQueryBuilder);
        //指定请求索引
        SearchRequest searchRequest=new SearchRequest(index);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse=highLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        return  searchResponse.getHits();
    }
}
