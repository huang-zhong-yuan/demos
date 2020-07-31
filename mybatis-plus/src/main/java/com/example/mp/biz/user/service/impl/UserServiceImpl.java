package com.example.mp.biz.user.service.impl;

import com.example.mp.infra.entity.User;
import com.example.mp.infra.mapper.UserMapper;
import com.example.mp.biz.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author center
 * @since 2020-08-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
