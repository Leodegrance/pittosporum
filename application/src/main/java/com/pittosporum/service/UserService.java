package com.pittosporum.service;

import pittosporum.core.ProcessResponse;
import pittosporum.dto.UserDto;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public interface UserService {
    ProcessResponse<UserDto> login(String name, String pwd);
}
