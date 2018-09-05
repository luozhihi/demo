package utils;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

// elastic search 6.x版本之后的一个index中只能有一个type
public class ESUtils {
    private TransportClient client = null;
    {
        try {
            client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
            // 集群则将新的节点的信息添加到对象中
            // 如果节点名字不是默认的名字，则手动设置
            Settings settings = Settings.builder()
                    .put("cluster.name", "myClusterName").build();
            //TransportClient client = new PreBuiltTransportClient(settings);
            System.out.println("连接es服务成功");
        } catch (UnknownHostException e) {
            System.out.println("连接es服务失败");
            e.printStackTrace();
        }
    }



    /**
     * 添加对象到指定的index，并且为其设置type
     *
     * @param index
     * @param type
     * @param object
     */
    public void add(String index, String type,Object id, Object object) {
        if (client != null) {
            try {
                String json = JSONObject.toJSONString(object);
                IndexResponse response = client.prepareIndex(index, type,id.toString())
                        .setSource(json, XContentType.JSON).get();
                String id1 = response.getId();
                System.out.println(id1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 查询
     * 从指定的索引库，指定的类型中获取指定字段为指定值的结果
     * @param indexs
     * @param types
     * @param fieldName
     * @param key
     * @return 返回结果的字符串
     */
    public <T> List<T> searchByFieldNameAndKey(String[] indexs, String[] types, String fieldName, String key,Class<T> tClass) {
        QueryBuilder queryBuilder = QueryBuilders.termQuery(fieldName, key);
        SearchResponse response = client.prepareSearch(indexs)
                .setTypes(types)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(queryBuilder)                 // Query
                .get();
        SearchHit[] hits = response.getHits().getHits();
        List<T> list = new ArrayList<T>();
        for (SearchHit hit : hits) {
            String json = hit.getSourceAsString();
            T t = JSONObject.parseObject(json, tClass);
            list.add(t);
        }
        return list;
    }

    /**
     * 通过index名字和type名字和id，查询数据
     * @param index
     * @param type
     * @param id
     * @return
     */
    public <T> T searchById(String index, String type, String id,Class<T> tClass) {
        GetResponse response = client.prepareGet(index, type, id).get();
//        System.out.println(response.getSourceAsString());
        String json = response.getSourceAsString();
        T t = JSONObject.parseObject(json, tClass);
        return t;
    }


    /**
     * 根据id删除index中保存的数据
     * @param index
     * @param type
     * @param id
     */
    public RestStatus deleteById(String index, String type, String id) {
        DeleteResponse deleteResponse = client.prepareDelete(index, type, id).get();
        return deleteResponse.status();
    }

    /**
     * 删除索引库
     * @param indexName
     * @return
     */
    public Boolean deleteIndexByName(String indexName) {
        DeleteIndexResponse dResponse = client.admin().indices().prepareDelete(indexName)
                .execute().actionGet();
        return dResponse.isAcknowledged();
    }

    /**
     * 关闭连接
     */
    public void closeClient() {
        if (client != null) {
            client.close();
        }
    }
}
