package com.esensetime.demo_web.feignInterface;

import entity.Topic;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "demo_service")
@RequestMapping("topicController/")
public interface ITopicFeign {

    @RequestMapping("addTopic")
    void addTopic(Topic topic);

    @RequestMapping("queryById")
    Topic queryById(Integer id);

    @RequestMapping("queryAll")
    List<Topic> queryAll();
}
