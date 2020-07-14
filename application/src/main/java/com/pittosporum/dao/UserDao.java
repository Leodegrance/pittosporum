package com.pittosporum.dao;

import com.pittosporum.entity.User;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public interface UserDao {
    User findUserById(String id);
    User findUserByName(String name);
}
