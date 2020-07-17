package com.pittosporum.service;

import pittosporum.constant.ProcessResponse;
import pittosporum.dto.UserDto;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public interface UserService {
    ProcessResponse<UserDto> login(String name, String pwd);
}
