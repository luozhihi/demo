package com.esensetime.demo_web.feignInterface;

import com.sensetime.entity.Topic;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "demo-service",path = "topicController")
public interface ITopicFeign {

    @RequestMapping(value = "addTopic",method = RequestMethod.POST)
    String addTopic(Topic topic);

    @RequestMapping(value = "queryById",method = RequestMethod.POST)
    Topic queryById(Integer id);

    @RequestMapping("queryAll")
    List<Topic> queryAll();

}
