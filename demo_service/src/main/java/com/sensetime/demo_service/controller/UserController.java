package com.sensetime.demo_service.controller;

import com.sensetime.demo_service.service.IUserService;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("userController/")
public class UserController {
    @Autowired
    private IUserService userService;

    @RequestMapping("register")
    public void register(User user) {
        userService.addUser(user);
    }

    @RequestMapping("login")
    public User login(User user) {
        if (user != null) {
            return userService.login(user);
        }
        return null;
    }
}
