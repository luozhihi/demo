package com.esensetime.demo_web.feignInterface;

import entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "demo_service")
@RequestMapping("userController/")
public interface IUserFeign {
    @RequestMapping("register")
    void register(User user);

    @RequestMapping("login")
    User login(User user);
}
