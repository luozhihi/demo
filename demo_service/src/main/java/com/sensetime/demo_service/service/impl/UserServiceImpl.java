package com.sensetime.demo_service.service.impl;

import com.sensetime.demo_service.dao.IUserDao;
import com.sensetime.demo_service.service.IUserService;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao userDao;
    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public User login(User user) {
        if(user != null) {
            User user2 = userDao.queryUserByUserName(user.getUsername());
            if (user2 != null && user.getPassword().equals(user.getPassword())) {
                return user2;
            }
        }
        return null;
    }

}
