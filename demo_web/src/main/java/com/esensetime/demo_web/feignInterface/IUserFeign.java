package com.esensetime.demo_web.feignInterface;

import com.sensetime.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "demo-service",path = "userController")
public interface IUserFeign {
    @RequestMapping(value = "register",method = RequestMethod.POST)
    void register(User user);

    @RequestMapping(value = "login",method = RequestMethod.POST)
    User login(User user);
}
