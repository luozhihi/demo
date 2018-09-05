package com.sensetime.demo_service.controller;

import com.sensetime.demo_service.service.ITopicService;
import entity.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@RequestMapping("topicController/")
@Controller
public class TopicController {
    @Autowired
    private ITopicService topicService;

    @RequestMapping("addTopic")
    public void addTopic(Topic topic) {
        topic.setTime(new Date());
        topicService.addTopic(topic);
    }

    @RequestMapping("queryById")
    public Topic queryById(Integer id){
        return topicService.queryById(id);
    }

    @RequestMapping("queryAll")
    public List<Topic> queryAll(){
        return topicService.queryAll();
    }
}
