package com.esensetime.demo_web.advice;

import com.sensetime.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Aspect
public class LoginAdvice{
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private RedisTemplate redisTemplate;


    @Around(value = "@annotation(annotation.Login)")
    public Object invoke(ProceedingJoinPoint point) throws Throwable {
        System.out.println("开始进行登陆拦截");
        Cookie[] cookies = request.getCookies();
        String loginFlag = null;
        if(cookies!=null){
            for (Cookie cookie :cookies){
                if(cookie.getName().equals("loginFlag")){
                    loginFlag = cookie.getValue();
                    break;
                }
            }
        }
        if(loginFlag != null){
            User user = (User)redisTemplate.opsForValue().get(loginFlag);
            if(user != null){
                Object[] args = point.getArgs();
                for(int i = 0 ;i < args.length;i++){
                    if(args[i] instanceof User){
                        args[i] = user;
                        break;
                    }
                }
               return point.proceed(args);
            }
        }
        request.setAttribute("error","请先登录！");
        return "login";
    }
}
