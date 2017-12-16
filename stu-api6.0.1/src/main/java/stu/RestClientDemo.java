package stu;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;

/**
 * 使用RestClient访问
 * <p>
 * | index
 * |-- type
 * |---- document
 * |------ field
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2017/12/13
 */
public class RestClientDemo {
    private static final Logger logger = LoggerFactory.getLogger(RestClientDemo.class);
    private static RestClient restClient =
            RestClient.builder(new HttpHost("10.211.55.7", 9200, "http"))
                    .build();


    /**
     * 0.查看api信息
     *
     * @throws Exception
     */
    @Test
    public void catApi() throws Exception {
        String method = "GET";
        String endpoint = "/_cat";
        Response response = restClient.performRequest(method, endpoint);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    /**
     * 1.执行一个基本的方法，验证es集群是否搭建成功
     */
    @Test
    public void test1Ping() throws IOException {
        Response response = restClient.performRequest("GET", "/");
        logger.info(EntityUtils.toString(response.getEntity()));
    }

    /**
     * 2.创建索引
     *
     * @throws IOException
     */
    @Test
    public void test2CreateIndex() throws IOException {
        Response res2 = restClient.performRequest("PUT", "/bob");
        logger.info(EntityUtils.toString(res2.getEntity()));

    }

    /**
     * 3.创建文档
     *
     * @throws IOException
     */
    @Test
    public void test3CreateDocument() throws IOException {
        HttpEntity entity3 = new NStringEntity("{\"user\": \"张三\",\"title\": \"工程师\",\"desc\": \"数据库管理\"}"
                , ContentType.APPLICATION_JSON);
        Response res3 = restClient.performRequest("POST", "/doc/document1"
                , Collections.<String, String>emptyMap(), entity3);
        logger.info(EntityUtils.toString(res3.getEntity()));

    }

    /**
     * 4.获取document的_id获取文档
     * <p>
     * url携带"?pretty"，查询出来的数据有格式化
     *
     * @throws IOException
     */
    @Test
    public void test4GetDocument() throws IOException {
        Response res4 = restClient.performRequest("GET", "/doc/document1/RDLTSmAB_Ubb77WHzC7l?pretty");
        logger.info(EntityUtils.toString(res4.getEntity()));
    }

    /**
     * 5.查询所有数据
     *
     * @throws IOException
     */
    @Test
    public void test5QryAll() throws IOException {
        HttpEntity entity5 = new NStringEntity("{\"query\": {\"match_all\":{}}}", ContentType.APPLICATION_JSON);
        Response res5 = restClient.performRequest("POST", "/doc/document1/_search"
                , Collections.<String, String>emptyMap(), entity5);
        logger.info(EntityUtils.toString(res5.getEntity()));
    }

    /**
     * 6.根据Field条件获取
     *
     * @throws Exception
     */
    @Test
    public void queryByField() throws Exception {
        HttpEntity entity = new NStringEntity("{\"query\": {\"match\":{\"user\":\"张三\"}}}\n"
                , ContentType.APPLICATION_JSON);
        Response response = restClient.performRequest("POST", "/doc/document1/_search?pretty"
                , Collections.<String, String>emptyMap(), entity);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }


    /**
     * 7.更新数据
     *
     * @throws Exception
     */
    @Test
    public void updateBy_ID() throws Exception {
        HttpEntity entity = new NStringEntity("{\"doc\":{\"user\": \"bob\",\"title\": \"java工程师\",\"desc\": \"吊炸天\"}}\n"
                , ContentType.APPLICATION_JSON);
        Response response = restClient.performRequest("POST", "/doc/document1/QzLNSmAB_Ubb77WH0S7A/_update?pretty"
                , Collections.<String, String>emptyMap(), entity);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }


}
