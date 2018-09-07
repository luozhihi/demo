package com.esensetime.demo_web.feignInterface;

import com.sensetime.entity.Topic;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "demo-es",path = "esController")
public interface IESFeign {

    @RequestMapping(value = "searchTopic",method = RequestMethod.POST)
    List<Topic> searchTopic(String key);
}
