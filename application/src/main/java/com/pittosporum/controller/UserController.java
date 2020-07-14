package com.pittosporum.controller;

import com.pittosporum.constant.AppErrorCode;
import com.pittosporum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pittosporum.core.ProcessResponse;
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

    @ResponseBody
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ProcessResponse<UserDto> login(@RequestParam Map<String,Object> params){
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
