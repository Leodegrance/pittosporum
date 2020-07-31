package com.pittosporum.service.impl;

import com.pittosporum.dao.UserDao;
import com.pittosporum.entity.User;
import com.pittosporum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pittosporum.constant.ProcessResponse;
import com.pittosporum.constant.app.AppErrorCode;
import com.pittosporum.dto.UserDto;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public ProcessResponse<UserDto> login(String name, String pwd) {
        User user = userDao.findUserByName(name);
        if (user == null){
            return ProcessResponse.failure(AppErrorCode.USER_NOT_EXIST.getStatusCode(), AppErrorCode.USER_NOT_EXIST.getMessage());
        }

        String ps = user.getPwd();
        if (!ps.equals(pwd)){
            return ProcessResponse.failure(AppErrorCode.PASSWORD_ERROR.getStatusCode(), AppErrorCode.PASSWORD_ERROR.getMessage());
        }

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        user.setMobileNumber(user.getMobileNumber());
        user.setName(user.getName());
        return ProcessResponse.success(userDto);
    }
}
