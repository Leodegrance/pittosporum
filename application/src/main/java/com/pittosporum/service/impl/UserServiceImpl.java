package com.pittosporum.service.impl;

import com.pittosporum.dao.UserDao;
import com.pittosporum.dto.UserDto;
import com.pittosporum.entity.User;
import com.pittosporum.service.UserService;
import com.pittosporum.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserDto login(String name) {
        User user = userDao.findUserByName(name);
        if (user == null){
            return null;
        }

        UserDto userDto = BeanUtil.copyProperties(user, UserDto.class);
        return userDto;
    }
}
