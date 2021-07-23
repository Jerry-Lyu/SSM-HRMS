package com.ssmhrms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author JIAJUN
 * @create 2021-07-22 21:20
 */
@Controller
@RequestMapping("/hrms")
public class LoginController {

    @RequestMapping("/goLogin")
    public String goLogin() {
        return "login";
    }

    @RequestMapping("/main")
    public String main(HttpSession session){
        return "main";
    }

    @RequestMapping("/login")
    public String login(HttpSession session, String username, String password, HttpServletRequest request) {
        if ("admin".equals(username) && "1234".equals(password)) {
            session.setAttribute("userLoginInfo", username);
            return "redirect:/hrms/main";
        } else {
            request.setAttribute("error", "用户名或者密码不正确");
            return "login";
        }

    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("userLoginInfo");
        return "login";
    }
}
