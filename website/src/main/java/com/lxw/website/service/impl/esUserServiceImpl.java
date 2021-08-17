package com.lxw.website.service.impl;

import com.lxw.website.domain.PageResult;
import com.lxw.website.domain.esuser;
import com.lxw.website.service.esUserService;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TotalHitCountCollector;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mission
 * @version 1.0
 * @description: TODO
 * @date 2021/8/2 14:08
 */
@Service
public class esUserServiceImpl implements esUserService {

    @Autowired
    private com.lxw.website.dao.esUserRepository esUserRepository;

    @Autowired
    ElasticsearchOperations elasticsearchOperations;


    //ElasticsearchRestTemplate是Spring封装ES客户端的一些原生api模板，方便实现一些查询，和ElasticsearchTemplate一样，
    // 但是目前spring推荐使用前者，是一种更高级的REST风格api。
    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public List<esuser> findAll() {
        return esUserRepository.findAll();
    }

    @Override
    public void deleteAll() {
        esUserRepository.deleteAll();
    }

    @Override
    public long getAllCount() {
        return esUserRepository.count();
    }

    @Override
    public List<esuser> findMaxuserId() {
        return null;
    }

    /**
     *
     * @param username
     * @return
     */
    @Override
    public List<esuser> findByUsername(String username) {
        return esUserRepository.findByUsernameStartingWith(username);
    }

    /**
     * 单个保存
     * @param esuser
     */
    @Override
    public void save(esuser esuser) {
        esUserRepository.save(esuser);
    }

    //批量保存
    @Override
    public void saveAll(List<esuser> esuser) {
        esUserRepository.saveAll(esuser);
    }

    @Override
    public PageResult getDate(Integer currentPage, Integer limit) {
        NativeSearchQuery query = new NativeSearchQuery(new BoolQueryBuilder());
        query.setPageable(PageRequest.of(currentPage, limit));
        SearchHits<esuser> searchHits = elasticsearchRestTemplate.search(query, esuser.class);
        //查询出总页码
        long count=elasticsearchRestTemplate.count(query,esuser.class);
        List<esuser> esusers = searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
        PageResult pageResult=new PageResult((int) count,currentPage,limit,esusers);
        return pageResult;
    }

    @Override
    public PageResult getgetweightsortpageDate(Integer currentPage, Integer limit) {
        String preTag = "<font color='#dd4b39'>";
        String postTag = "</font>";
        NativeSearchQuery nativeSearchQuery=new NativeSearchQueryBuilder()
                //queryStringQuery  指定字符串作为关键词查询，关键词支持分词
                //.withQuery(QueryBuilders.queryStringQuery("我会怎样想念它并且梦").field("username"))
                //.withQuery(QueryBuilders.termQuery("username","想念"))
                .withQuery(QueryBuilders.matchQuery("username","我会怎样想念它并且梦"))
                .withPageable(PageRequest.of(currentPage, limit))
                .withSort(SortBuilders.fieldSort("userid").order(SortOrder.DESC))
                .withHighlightFields(new HighlightBuilder.Field("username").preTags(preTag).postTags(postTag))
                .build();

        SearchHits<esuser> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, esuser.class);

        //long count=elasticsearchRestTemplate.count(nativeSearchQuery,esuser.class);

        List<esuser> esusers = searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
        PageResult pageResult=new PageResult((int) searchHits.getTotalHits(),currentPage,limit,esusers);
        return pageResult;
    }


}
