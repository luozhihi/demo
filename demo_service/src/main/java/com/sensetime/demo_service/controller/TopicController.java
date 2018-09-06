package com.sensetime.demo_service.controller;

import com.sensetime.demo_service.service.ITopicService;
import com.sensetime.entity.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RequestMapping("topicController/")
@RestController
public class TopicController {
    @Autowired
    private ITopicService topicService;

    @RequestMapping(value = "addTopic",method = RequestMethod.POST)
    public Topic addTopic(@RequestBody Topic topic) {
        topic.setTime(new Date());
        return topicService.addTopic(topic);
    }

    @RequestMapping(value = "queryById",method = RequestMethod.POST)
    public Topic queryById(@RequestBody Integer id){
        System.out.println(id);
        return topicService.queryById(id);
    }

    @RequestMapping(value = "queryAll")
    public List<Topic> queryAll(){
        return topicService.queryAll();
    }
}
