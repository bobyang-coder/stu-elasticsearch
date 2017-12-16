package stu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Elasticsearch index api学习
 * 版本： v6.0.1
 * 官方链接：https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-docs-index.html
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2017/12/13
 */
public class IndexApiDemo {
    private static final Logger logger = LoggerFactory.getLogger(IndexApiDemo.class);

    /**
     * 手动拼写json串
     */
    @Test
    public void genJson1() {
        String json = "{" +
                "\"user\":\"bob\"," +
                "\"postDate\":\"2017年12月13日\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
    }

    /**
     * 使用集合
     * <p>
     * 集合是key:value数据类型，可以代表json结构.
     */
    @Test
    public void genJson2() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("user", "kimchy");
        json.put("postDate", new Date());
        json.put("message", "trying out Elasticsearch");
    }

    /**
     * 使用Jackson将bean对象转换为json
     */
    @Test
    public void genJson3() throws JsonProcessingException {
        Object bean = new Object();
        ObjectMapper mapper = new ObjectMapper(); // create once, reuse
        // generate json
        byte[] json = mapper.writeValueAsBytes(bean);
    }

    /**
     * 使用ElasticSearch提供的帮助类
     * <p>
     * field方法支持很多对象类型。(数字、日期甚至XContentBuilder对象)
     */
    @Test
    public void genJson4() throws IOException {
        //创建对象
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .field("user", "kimchy")
                .field("postDate", new Date())
                .field("message", "trying out Elasticsearch")
                .endObject();

        String json = builder.string();
        logger.info(json);//{"user":"kimchy","postDate":"2017-12-13T11:50:13.328Z","message":"trying out Elasticsearch"}

        //创建数组
        XContentBuilder builder2 = XContentFactory.jsonBuilder()
                .startArray()
                .array("user", "kimchy")
                .array("postDate")
                .array("message", "trying out Elasticsearch")
                .endArray();
        String json2 = builder2.string();
        logger.info(json2);

    }

}
