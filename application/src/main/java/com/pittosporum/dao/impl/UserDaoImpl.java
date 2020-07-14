package com.pittosporum.dao.impl;

import com.pittosporum.dao.UserDao;
import com.pittosporum.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pittosporum.dto.SQLStoreDto;

import java.util.List;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Service
public class UserDaoImpl implements UserDao {
    @Autowired
    @Qualifier("oRumJdbcTemplate")
    private JdbcTemplate oRumJdbcTemplate;

    @Override
    public User findUserById(String id) {
        String sql = "SELECT id, name, pwd, email, mobile_number, create_by, create_dt, update_by, update_dt, status FROM user_ent where status = 'AT0001' and id = ?;";
        return oRumJdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
    }

    @Override
    public User findUserByName(String name) {
        String sql = "SELECT id, name, pwd, email, mobile_number, create_by, create_dt, update_by, update_dt, status FROM user_ent where status = 'AT0001' and name = ?;";

        List<User> list = oRumJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), name);
        return DataAccessUtils.uniqueResult(list);
    }
}
