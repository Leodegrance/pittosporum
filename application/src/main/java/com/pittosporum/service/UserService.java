package com.pittosporum.service;

import com.pittosporum.constant.ProcessResponse;
import com.pittosporum.dto.UserDto;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public interface UserService {
    ProcessResponse<UserDto> login(String name, String pwd);
}
