package com.esensetime.demo_web.controller;

import com.esensetime.demo_web.feignInterface.ITopicFeign;
import com.esensetime.demo_web.feignInterface.IUserFeign;
import entity.Topic;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String addTopic(Topic topic) {
        topicFeign.addTopic(topic);
        return "redirect:queryTopicList";
    }

    @RequestMapping("queryTopicById")
    public String queryById(Integer id,Model model){
        Topic topic = topicFeign.queryById(id);
        model.addAttribute(topic);
        return "topic";
    }

    @RequestMapping("queryTopicList")
    public String queryTopicList(Model model){
        List<Topic> topics = topicFeign.queryAll();
        model.addAttribute("topicList",topics);
        return "list";
    }
}
