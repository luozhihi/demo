package com.sensetime.demo_service.dao;

import com.sensetime.entity.Topic;

import java.util.List;

public interface ITopicDao {
    void addTopic(Topic topic);
    Topic queryById(Integer id);
    List<Topic> queryAll();
}
