package com.sensetime.demo_service.service;

import com.sensetime.entity.User;

public interface IUserService {
    void addUser(User user);

    User login(User user);

}
