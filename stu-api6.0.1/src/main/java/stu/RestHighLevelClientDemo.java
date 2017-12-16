package stu;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

/**
 * Elsearch7.0 以后推荐使用RestHighLevelClient访问ø
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2017/12/12
 */
public class RestHighLevelClientDemo {

    private static final Logger logger = LoggerFactory.getLogger(RestHighLevelClientDemo.class);
    private static final RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(new HttpHost("10.211.55.7", 9200, "http")));

    @Test
    public void ping() throws IOException {
        boolean ping = client.ping();
        logger.info(String.valueOf(ping));
    }

    //======================================================================================
    //======================================基础篇：增删改查======================================
    //======================================================================================

    /**
     * 增：创建文档
     *
     * @throws IOException
     */
    @Test
    public void index() throws IOException {
        IndexResponse response = client.index(new IndexRequest().index("doc2").type("document2").id("2")
                .source(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("user", "BOB")
                        .field("postDate", new Date())
                        .field("message", "😁")
                        .field("updateDate", new Date())
                        .field("type", "document2")
                        .endObject()));
        logger.info(response.toString());

        //IndexResponse[index=doc,type=document1,id=1,version=2,result=updated,seqNo=1,primaryTerm=2,shards={"total":2,"successful":1,"failed":0}]
        //IndexResponse[index=doc,type=document1,id=1,version=3,result=updated,seqNo=2,primaryTerm=2,shards={"total":2,"successful":1,"failed":0}]
    }

    /**
     * 查：搜索
     */
    @Test
    public void search() throws IOException {
        SearchResponse search = client.search(new SearchRequest().indices("doc/document1"));
        logger.info(search.toString());
    }

    /**
     * 查：获取
     *
     * @throws IOException
     */
    @Test
    public void get() throws IOException {
        //根据id查询
        GetResponse response = client.get(new GetRequest("doc", "document1", "1"));
        logger.info(response.toString());

        //根据index查询
        GetResponse response2 = client.get(new GetRequest("doc").id("1"));
        logger.info(response2.toString());

    }


}
