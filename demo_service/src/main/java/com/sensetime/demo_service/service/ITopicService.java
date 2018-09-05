package com.sensetime.demo_service.service;

import entity.Topic;

import java.util.List;

public interface ITopicService {
    void addTopic(Topic topic);
    Topic queryById(Integer id);
    List<Topic> queryAll();
}
