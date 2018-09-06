package com.esensetime.demo_web.controller;

import annotation.Login;
import com.esensetime.demo_web.feignInterface.ITopicFeign;
import com.esensetime.demo_web.feignInterface.IUserFeign;
import com.sensetime.entity.Topic;
import com.sensetime.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import utils.ESUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class FrontController {
    @Autowired
    private IUserFeign userFeign;
    @Autowired
    private ITopicFeign topicFeign;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("register")
    public String register(User user) {
        userFeign.register(user);
        return "index";
    }

    @RequestMapping("login")
    public String login(User user, HttpServletResponse response, Model model) {
        User result = userFeign.login(user);
        if (result != null) {
            String key = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(key, result, 30, TimeUnit.DAYS);
            Cookie cookie = new Cookie("loginFlag", key);
            cookie.setPath("/");
            cookie.setMaxAge(1000 * 60 * 60 * 24 * 30);
            response.addCookie(cookie);
            return "list";
        }
        model.addAttribute("error", "用户名或密码不正确");
        return "login";
    }

    @RequestMapping("addTopic")
    @Login
    public String addTopic(@CookieValue(name = "loginFlag", required = false) String key, Topic topic, Model model) {
        if (key != null) {
            User user = (User) redisTemplate.opsForValue().get(key);
            System.out.println(user);
            if (user != null) {
                topic.setAuthorName(user.getName());
                topic.setUserId(user.getId());
                String topicJson = topicFeign.addTopic(topic);
                kafkaTemplate.send("topic-1", topicJson);
                return "redirect:queryTopicList";
            }
        }
        model.addAttribute("error", "请先登录");
        return "login";
    }

    @RequestMapping("queryTopicById")
    public String queryById(Integer id, Model model) {
        System.out.println(id);
        Topic topic = topicFeign.queryById(id);
        System.out.println(topic);
        model.addAttribute(topic);
        return "topic";
    }

    @RequestMapping("queryTopicList")
    public String queryTopicList(Model model) {
        List<Topic> topics = topicFeign.queryAll();
        model.addAttribute("topicList", topics);
        System.out.println(topics);
        return "list";
    }

    @RequestMapping("searchTopic")
    public String searchTopic(String key, Model model) {
        ESUtils esUtils = new ESUtils();
        String[] indexs = {"topic"};
        String[] types = {"topic"};
        List<Topic> lists = esUtils.searchByFieldNameAndKey(indexs, types, "content", key, Topic.class);
        model.addAttribute("topicList", lists);
        model.addAttribute("key", key);
        esUtils.closeClient();
        return "list";
    }
}
