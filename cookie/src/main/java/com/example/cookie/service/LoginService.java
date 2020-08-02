package com.example.cookie.service;

import com.example.cookie.entity.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface LoginService {
    UserInfo getUserInfoByAccount(String cookie_username);

    String doLogin(String trim, String trim1, HttpSession session, HttpServletRequest request, HttpServletResponse response);
}
