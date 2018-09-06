package com.esensetime.demo_web.advice;

import com.sensetime.entity.User;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginAdvice implements MethodInterceptor {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    @Around(value = "@annotation(annotation.Login)")
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("检查登陆信息");
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie :cookies){
            if(cookie.getName().equals("loginFlag")){
                User user = (User)redisTemplate.opsForValue().get(cookie.getValue());
                request.setAttribute("authorName",user.getName());
                request.setAttribute("userId",user.getId());
                return methodInvocation.proceed();
            }
        }
        request.setAttribute("error","请先登陆");
        request.getRequestDispatcher("login.html").forward(request,response);
        return null;
    }
}
