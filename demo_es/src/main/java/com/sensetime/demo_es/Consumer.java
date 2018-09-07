package com.sensetime.demo_es;

import com.alibaba.fastjson.JSONObject;
import com.sensetime.entity.Topic;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import utils.ESUtils;

@Component
public class Consumer {

    @KafkaListener(topics = {"topic-1"})
    public void msgConsumer(String content){
        System.out.println(content);
        Topic topic = JSONObject.parseObject(content, Topic.class);
        ESUtils esUtils = new ESUtils();
        esUtils.add("topic", "topic", topic.getId(), topic);
        esUtils.closeClient();
        System.out.println("添加到索引库成功");
    }
}
