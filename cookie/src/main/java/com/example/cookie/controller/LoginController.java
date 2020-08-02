package com.example.cookie.controller;

import com.example.cookie.constants.Const;
import com.example.cookie.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/v1")
public class LoginController {
    @Autowired
    private LoginService service;

    @PostMapping("/login")
    @ResponseBody
    public String login(String username, String password, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        return service.doLogin(username.trim(), password.trim(), session, request, response);
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        // 删除session里面的用户信息
        session.removeAttribute(Const.SYSTEM_USER_SESSION);
        // 保存cookie，实现自动登录
        Cookie cookie_username = new Cookie("cookie_username", "");
        // 设置cookie的持久化时间，0
        cookie_username.setMaxAge(0);
        // 设置为当前项目下都携带这个cookie
        cookie_username.setPath(request.getContextPath());
        // 向客户端发送cookie
        response.addCookie(cookie_username);
        return "login";
    }
}
