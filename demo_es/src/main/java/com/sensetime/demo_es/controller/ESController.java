package com.sensetime.demo_es.controller;

import com.sensetime.entity.Topic;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.ESUtils;

import java.util.List;

@RestController
@RequestMapping("esController")
public class ESController {

    @RequestMapping("searchTopic")
    public List<Topic> searchTopicByKey(String key){
        ESUtils esUtils = new ESUtils();
        String[] indexs = {"topic"};
        String[] types = {"topic"};
        List<Topic> lists = esUtils.searchByFieldNameAndKey(indexs, types, "content", key, Topic.class);
        esUtils.closeClient();
        return lists;
    }
}
