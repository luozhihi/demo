package com.sensetime.demo_service.dao;

import com.sensetime.entity.User;

public interface IUserDao {

    void addUser(User user);

    User queryUserByUserName(String username);
}
