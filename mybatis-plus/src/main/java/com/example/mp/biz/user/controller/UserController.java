package com.example.mp.biz.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mp.biz.user.service.IUserService;
import com.example.mp.infra.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/public/user")
@Slf4j
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/list")
    public List<User> getUserList() {
        List<User> users = userService.list(new QueryWrapper<User>().lambda().le(User::getAge, 50));
        log.info("users.size == " + users.size());
        return users;
    }
}
