package com.sensetime.demo_service.controller;

import com.sensetime.demo_service.service.IUserService;
import com.sensetime.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("userController/")
public class UserController {
    @Autowired
    private IUserService userService;

    @RequestMapping(value = "register",method = RequestMethod.POST)
    public void register(@RequestBody User user) {
        System.out.println(user);
        userService.addUser(user);
    }

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public User login(@RequestBody User user) {
        if (user != null) {
            return userService.login(user);
        }
        return null;
    }
}
