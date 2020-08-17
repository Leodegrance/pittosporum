package com.pittosporum.controller;

import com.pittosporum.constant.ProcessResponse;
import com.pittosporum.constant.app.AppErrorCode;
import com.pittosporum.dto.UserDto;
import com.pittosporum.entity.TokenInfo;
import com.pittosporum.service.UserService;
import com.pittosporum.utils.CommonUtil;
import com.pittosporum.utils.TokenUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author yichen(graffitidef @ gmail.com)
 */
@RestController
@RequestMapping(value = "/user-mgr")
public class UserController {

    @Autowired
    private UserService userService;

    private static Map<String, TokenInfo> tokenInfoMap = new HashedMap();

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ProcessResponse<String> login(@RequestHeader(name = "token") String tokenStr, @RequestParam(name = "userName") String userName, @RequestParam(name = "userPwd") String userPwd){
        if (CommonUtil.isEmpty(tokenStr) || CommonUtil.isEmpty(userName) || CommonUtil.isEmpty(userPwd)){
            return ProcessResponse.failure(AppErrorCode.PARAMS_IS_EMPTY.getStatusCode(), AppErrorCode.PARAMS_IS_EMPTY.getMessage());
        }

        UserDto userDto = userService.login(userName);
        if (userDto == null){
            return ProcessResponse.failure(AppErrorCode.USER_NOT_EXIST.getStatusCode(), AppErrorCode.USER_NOT_EXIST.getMessage());
        }

        if (!userDto.getPwd().equals(userPwd)){
            return ProcessResponse.failure(AppErrorCode.PASSWORD_ERROR.getStatusCode(), AppErrorCode.PASSWORD_ERROR.getMessage());
        }

        String sign = TokenUtil.generateToken(userDto);
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setUserName(userDto.getName());
        tokenInfo.setSecret(userDto.getPwd());
        tokenInfo.setTokenStr(tokenStr);
        tokenInfoMap.put(tokenStr, tokenInfo);
        return ProcessResponse.success(sign);
    }
}
