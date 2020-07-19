package com.pittosporum.controller;

import com.pittosporum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pittosporum.constant.ProcessResponse;
import pittosporum.constant.app.AppErrorCode;
import pittosporum.dto.UserDto;
import pittosporum.utils.CommonUtil;

import java.util.Map;

/**
 * @author yichen(graffitidef @ gmail.com)
 */
@RestController
@RequestMapping(value = "/user-mgr")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ProcessResponse<UserDto> login(@RequestBody Map<String,Object> params){
        if (CommonUtil.isEmpty(params)){
            return ProcessResponse.failure(AppErrorCode.PARAMS_IS_EMPTY.getStatusCode(), AppErrorCode.PARAMS_IS_EMPTY.getMessage());
        }

        String name = (String) params.get("userName");
        String pwd = (String) params.get("userPwd");

        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(pwd)){
            return ProcessResponse.failure(AppErrorCode.PARAMS_IS_EMPTY.getStatusCode(), AppErrorCode.PARAMS_IS_EMPTY.getMessage());
        }

        return userService.login(name, pwd);
    }
}
