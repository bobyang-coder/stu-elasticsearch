package stu;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by bob on 2017/12/13.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2017/12/13
 */
public class JestClientDemo {

    public static void main(String[] args) throws UnknownHostException {
        //创建client
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
//                .addTransportAddress(new TransportAddress(InetAddress.getByName("10.211.55.7"), 9300))
                .addTransportAddress(new TransportAddress(InetAddress.getByName("10.211.55.7"), 9300));

        ActionFuture<GetResponse> future = client.get(new GetRequest("/"));
        // on shutdown
        client.close();

    }
}
