package com.lxw.website.utils.ES;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import com.lxw.website.domain.PageResult;
import com.lxw.website.domain.esuser;
import com.lxw.website.es.EsConfig;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author mission
 * @version 1.0
 * @description: TODO
 * @date 2021/8/11 16:27
 */
@Component
public class Esutil<T> {

    @Resource(description = "getRestHighLevelClient")
    private RestHighLevelClient restHighLevelClient;


    /**
     * ????????????????????????
     * @param index
     * @return
     * @throws IOException
     */
    public boolean isIndexExit(String index) throws IOException {
        boolean isExit=false;
        GetIndexRequest getIndexRequest=new GetIndexRequest(index);
        isExit=restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        return isExit;
    }

    /**
     * ??????????????????
     * @return
     * @throws IOException
     */
    public Object addDate() throws IOException {
        IndexRequest indexRequest=new IndexRequest("es_test");
        indexRequest.timeout(TimeValue.timeValueSeconds(20));
        esuser es=new esuser(1,"lxw","393149695@qq.com","???","2012-09-08 08:47:47",12,"???????????????");
        indexRequest.source(JSON.toJSONString(es),XContentType.JSON);
        IndexResponse indexResponse=restHighLevelClient.index(indexRequest,RequestOptions.DEFAULT);
        return  indexResponse;

    }
    /**
     * ??????????????????
     * @return
     * @throws IOException
     */
    public Object addDates() throws IOException {
        BulkRequest bulkRequest=new BulkRequest();
        bulkRequest.timeout(TimeValue.timeValueSeconds(20));
        for(int i=0;i<=100;i++){
            IndexRequest indexRequest=new IndexRequest("es_test");
            esuser es=new esuser(1,"lxw"+i,"393149695@qq.com","???","2012-09-08 08:47:47",12,"???????????????");
            indexRequest.source(JSON.toJSONString(es),XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulkItemResponses=restHighLevelClient.bulk(bulkRequest,RequestOptions.DEFAULT);
        return bulkItemResponses;
    }

    /**
     * ????????????
     * @return
     * @throws IOException
     */
    public Object delateDate() throws IOException {
        DeleteByQueryRequest deleteByQueryRequest=new DeleteByQueryRequest("es_test");
        deleteByQueryRequest.setQuery(new MatchQueryBuilder("userid",1));
        BulkByScrollResponse deleteResponse=restHighLevelClient.deleteByQuery(deleteByQueryRequest,RequestOptions.DEFAULT);
        return  deleteResponse;

    }

    /**
     * ????????????
     * @return
     * @throws IOException
     */
    public Object update() throws IOException {
        UpdateByQueryRequest updateByQueryRequest=new UpdateByQueryRequest("es_test");
        updateByQueryRequest.setTimeout("20S");
        updateByQueryRequest.setConflicts("proceed");
        String s=",ces";
        //updateByQueryRequest.setQuery(new TermQueryBuilder("userid","1"));
        RangeQueryBuilder queryBuilder=new RangeQueryBuilder("userid");
        queryBuilder.gte(0);
        updateByQueryRequest.setQuery(queryBuilder);
        updateByQueryRequest.setScript(new Script(ScriptType.INLINE,"painless","if (ctx._source.userid > 0) {ctx._source.username=ctx._source.username+'?????????'+'"+s+"'; }" , Collections.emptyMap()));
        BulkByScrollResponse bulkByScrollResponse=restHighLevelClient.updateByQuery(updateByQueryRequest,RequestOptions.DEFAULT);
        return bulkByScrollResponse    ;
    }

    /**
     * ????????????
     * @param index
     * @return
     * @throws IOException
     */
    public Object getIndexDate(String index) throws IOException {
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);
        //?????????????????????
        searchSourceBuilder.trackTotalHits(true);
        //??????????????????
        TimeValue timeValue=new TimeValue(60 ,TimeUnit.SECONDS);
        searchSourceBuilder.timeout(timeValue);
        //?????????????????????
        String[] includes=new String[]{"createtime","text","username","age","userid","email","gender"};
        //??????????????????
        ///String[] excludeFields = new String[] {"createtime"};
        searchSourceBuilder.fetchSource(includes, new String[]{});
        //match ???????????????
        //MatchQueryBuilder matchQueryBuilder=new MatchQueryBuilder("username","??????????????????????????????");
        //Range??????
        //RangeQueryBuilder queryBuilder=new RangeQueryBuilder("userid");
        //queryBuilder.gte(0); queryBuilder.lte(10000000);
        //????????????
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //boolQueryBuilder.must(matchQueryBuilder);
        //boolQueryBuilder.must(queryBuilder);

        searchSourceBuilder.query(boolQueryBuilder);
        //??????????????????
        SearchRequest searchRequest=new SearchRequest(index);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse=restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        return getPageResult(searchResponse);
    }

    /**
     *  ???????????????????????????
     * @param searchResponse  ?????????????????????
     * @return
     */
    public PageResult getPageResult(SearchResponse searchResponse){
        PageResult pageResult=new PageResult();
        List<Object> List=new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();//???????????????
            List.add(sourceAsMap);
        }
        pageResult.setDataList(List);
        pageResult.setLimit(10);pageResult.setTotalCount((int)searchResponse.getHits().getTotalHits().value);
        return pageResult;
    }
}
