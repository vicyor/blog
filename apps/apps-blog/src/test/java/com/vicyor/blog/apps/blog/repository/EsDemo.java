package com.vicyor.blog.apps.blog.repository;

import com.vicyor.blog.apps.blog.domain.EsBlog;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.PipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者:姚克威
 * 时间:2019/9/9 16:27
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class EsDemo {
    @Autowired
    private TransportClient transportClient;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void testArray() {
        MatchQueryBuilder termQuery = QueryBuilders.matchQuery("tag", "李白");
        SearchQuery searchQuery = new NativeSearchQuery(termQuery);
        searchQuery.addIndices("blog");
        searchQuery.addTypes("blog");
        AggregatedPage<EsBlog> aggregatedPage = elasticsearchTemplate.queryForPage(searchQuery, EsBlog.class);
        System.out.println(aggregatedPage.getContent());
    }

    /**
     * 根据id查询
     */
    @Test
    public void testGet() {
        GetRequestBuilder builder = transportClient.prepareGet().setFetchSource(true).setIndex("lib2").setType("book").setId("3");
        ActionFuture<GetResponse> future = builder.execute();
        GetResponse response = future.actionGet();
        System.out.println(response.getSourceAsString());
    }

    /**
     * 创建document
     */
    @Test
    public void testPut() throws IOException {
        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder().startObject()
                .field("id", "1")
                .field("title", "idea")
                .field("price", "288")
                .field("content", "我是好人").endObject();
        IndexResponse response = transportClient.prepareIndex("lib2", "book", "3").setSource(xContentBuilder).get();
        System.out.println(response);
    }

    /**
     * 删除document
     */
    @Test
    public void testDelete() throws IOException {
        DeleteResponse deleteResponse = transportClient.prepareDelete("lib2", "book", "3").get();
        System.out.println(deleteResponse);
    }

    /**
     * 更新文档
     */
    @Test
    public void testUpdate() throws Exception {
        UpdateRequest request = new UpdateRequest();
        request
                .index("lib2")
                .type("book")
                .id("3")
                .doc(
                        XContentFactory
                                .jsonBuilder()
                                .startObject()
                                .field("content", "更新后内容")
                                .endObject()
                )
        ;
        UpdateResponse updateResponse = transportClient.update(request).get();
        System.out.println(updateResponse);
    }

    /**
     * upset
     */
    @Test
    public void testUpdateSet() throws Exception {
        //添加
        IndexRequest request = new IndexRequest("lib2", "book", "2")
                .source(
                        XContentFactory
                                .jsonBuilder()
                                .startObject()
                                .field("id", "2")
                                .field("title", "工厂模式")
                                .field("content", "哈哈")
                                .endObject()
                );
        //更新
        UpdateRequest updateRequest = new UpdateRequest("lib2", "book", "2")
                .doc(XContentFactory
                        .jsonBuilder()
                        .startObject()
                        .field("id", "2")
                        .field("title", "工厂模式")
                        .field("content", "哈哈")
                        .endObject())
                .upsert(request);
        ActionFuture<UpdateResponse> future = transportClient.update(updateRequest);
        System.out.println(future.get());
    }

    /**
     * MGet
     */
    @Test
    public void testMGet() {
        MultiGetResponse responses = transportClient
                .prepareMultiGet()
                .add("lib2", "book", "1", "2", "3")
                .add("lib4", "user", "1", "2", "3")
                .get();
        for (MultiGetItemResponse response : responses) {
            System.out.println(response.getResponse().getSourceAsString());
        }
    }

    /**
     * bulk批量操作
     */
    @Test
    public void testBulk() throws Exception {
        BulkRequestBuilder builder = transportClient.prepareBulk();
        BulkResponse bulkResponse = builder
                .add(
                        transportClient.prepareIndex("lib2", "book", "88")
                                .setSource(XContentFactory
                                        .jsonBuilder()
                                        .startObject()
                                        .field("title", "java")
                                        .field("content", "java是世界上最好的语言")
                                        .endObject()
                                )
                )
                .add(transportClient.prepareIndex("lib2", "book", "88")
                        .setSource(XContentFactory
                                .jsonBuilder()
                                .startObject()
                                .field("title", "scala")
                                .field("content", "python是世界上最好的语言")
                                .endObject()
                        ))
                .execute().get();
        BulkItemResponse[] items = bulkResponse.getItems();
        for (BulkItemResponse item : items) {
            System.out.println(item.getResponse());
        }
    }

    /**
     * 查询删除
     */
    @Test
    public void testGetAndDelete() throws Exception {
        BulkByScrollResponse response = DeleteByQueryAction.INSTANCE
                .newRequestBuilder(transportClient)
                .filter(QueryBuilders.matchQuery("content", "编程语言"))
                .source("lib2")
                .get();
        response.getDeleted();
    }

    /**
     * match_all
     */
    @Test
    public void testMatchAll() throws Exception {
        MatchAllQueryBuilder builder = QueryBuilders.matchAllQuery();
        SearchResponse response = transportClient
                .prepareSearch("lib2")
                .setQuery(builder)
                .setSize(3).get();
        for (SearchHit hit : response.getHits()) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * match
     */
    @Test
    public void testMatch() throws Exception {
        MatchQueryBuilder builder = QueryBuilders.matchQuery("content", "java编程语言");
        SearchResponse response = transportClient
                .prepareSearch("lib2")
                .setQuery(builder)
                .setSize(3)
                .get();
        for (SearchHit hit : response.getHits()) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * Multi match
     *
     * @throws Exception
     */
    @Test
    public void testMultiMatch() throws Exception {
        MultiMatchQueryBuilder builder = QueryBuilders.multiMatchQuery("java", "content", "title");
        SearchResponse response = transportClient.prepareSearch("lib2").setQuery(builder).setSize(3).get();
        for (SearchHit hit : response.getHits()) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * term查询
     */
    @Test
    public void testTerm() {
        TermQueryBuilder builder = QueryBuilders.termQuery("name", "帅");
        SearchResponse response = transportClient.prepareSearch("lib4").setQuery(builder).setSize(3).get();
        for (SearchHit hit : response.getHits()) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * 各种查询
     */
    @Test
    public void testAllQuery() {
        //范围查询
        QueryBuilders
                .rangeQuery("birthday")
                .from("1998-04-01").to("2019-09-09");
        //前缀查询
        PrefixQueryBuilder prefixQuery = QueryBuilders.prefixQuery("name", "姚");
        //模糊查询
        QueryBuilders.fuzzyQuery("content", "编程概念");
        //bool查询
        QueryBuilders.boolQuery().must(prefixQuery);
        //通配符查询
        QueryBuilders.wildcardQuery("name", "zhao*");
        //type查询
        QueryBuilders.typeQuery("blog");
        //id查询
        QueryBuilders.idsQuery().addIds("1", "2");
    }

    //聚合查询
    @Test
    public void test() {
        //分组
        TermsAggregationBuilder groupBuilder = AggregationBuilders.terms("agggroup").field("name");
        //max
        MaxAggregationBuilder max = AggregationBuilders.max("aggMax").field("age");
        SearchResponse response = transportClient.prepareSearch("lib2").addAggregation(max).get();
        Max aggMax = response.getAggregations().get("aggMax");
        System.out.println(aggMax.getValue());
    }

    //query string
    @Test
    public void testQueryString() {
        QueryStringQueryBuilder builder = QueryBuilders.queryStringQuery("帅");
        SearchResponse response = transportClient.prepareSearch("lib2").setQuery(builder).setSize(3).get();
        for (SearchHit hit : response.getHits()) {
            System.out.println(hit.getSourceAsString());
        }
    }

}
