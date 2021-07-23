package com.ssmhrms.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author JIAJUN
 * @create 2021-07-22 21:55
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        //放行：判断什么情况下登陆
        //登陆页面放行
        if (request.getRequestURI().contains("goLogin")){
            return true;
        }
        if (request.getRequestURI().contains("login")){
            return true;
        }

        if(session.getAttribute("userLoginInfo") != null){
            return true;
        }

        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        return false;
    }
}
