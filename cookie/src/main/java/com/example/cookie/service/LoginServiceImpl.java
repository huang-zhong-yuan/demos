package com.example.cookie.service;

import com.alibaba.fastjson.JSONObject;
import com.example.cookie.constants.Const;
import com.example.cookie.entity.UserInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class LoginServiceImpl implements LoginService {
    @Override
    public UserInfo getUserInfoByAccount(String cookie_username) {
        return UserInfo.builder().userName("zhangsan").build();
    }

    @Override
    public String doLogin(String username, String password, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        // 最终返回的对象
        JSONObject res = new JSONObject();
        res.put("code", 0);
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            res.put("msg", "请输入手机号或密码");
            return res.toJSONString();
        }
        UserInfo dbUser = getUserInfoByAccount(username);
        if (null == dbUser) {
            res.put("msg", "该账号不存在，请检查后重试");
            return res.toJSONString();
        }
        // 验证密码是否正确
        String newPassword = password;
        // 将登录用户信息保存到session中
        session.setAttribute(Const.SYSTEM_USER_SESSION, dbUser);
        // 保存cookie，实现自动登录
        Cookie cookie_username = new Cookie("cookie_username", username);
        // 设置cookie的持久化时间，30天
        cookie_username.setMaxAge(30 * 24 * 60 * 60);
        // 设置为当前项目下都携带这个cookie
        cookie_username.setPath(request.getContextPath());
        // 向客户端发送cookie
        response.addCookie(cookie_username);
        res.put("code", 1);
        res.put("msg", "登录成功");
        return res.toJSONString();
    }
}
