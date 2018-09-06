package com.sensetime.demo_service.service.impl;

import com.sensetime.demo_service.dao.ITopicDao;
import com.sensetime.demo_service.service.ITopicService;
import com.sensetime.entity.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TopicServiceImpl implements ITopicService {
    @Autowired
    private ITopicDao topicDao;
    @Override
    public Topic addTopic(Topic topic) {
        topicDao.addTopic(topic);
        return topic;
    }

    @Override
    public Topic queryById(Integer id) {
        return topicDao.queryById(id);
    }

    @Override
    public List<Topic> queryAll() {
        return topicDao.queryAll();
    }
}
