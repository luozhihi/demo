package com.sensetime.demo_service;

import com.sensetime.demo_service.dao.IUserDao;
import com.sensetime.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoServiceApplicationTests {

	@Autowired
	private IUserDao userDao;
	@Test
	public void contextLoads() {
        User user = new User();
        user.setName("admin");
        user.setPassword("123");
        user.setName("小明");
        userDao.addUser(user);
	}

}
