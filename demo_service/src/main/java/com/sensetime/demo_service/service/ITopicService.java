package com.sensetime.demo_service.service;

import com.sensetime.entity.Topic;

import java.util.List;

public interface ITopicService {
    Topic addTopic(Topic topic);
    Topic queryById(Integer id);
    List<Topic> queryAll();
}
