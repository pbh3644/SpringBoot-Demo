package com.service.impl;

import com.base.service.impl.BaseServiceImpl;
import com.entity.User;
import com.mapper.UserMapper;
import com.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author pangbohuan
 * @description
 * @date 2019-06-06 12:05
 **/
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {
}
