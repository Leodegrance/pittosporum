package com.pittosporum.dao.impl;

import com.pittosporum.dao.UserDao;
import com.pittosporum.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pittosporum.xmlsql.XmlSQLMapper;

import java.util.List;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Service
public class UserDaoImpl implements UserDao {
    @Autowired
    @Qualifier("appJdbcTemplate")
    private JdbcTemplate appJdbcTemplate;

    @Override
    public User findUserById(String id) {
        String sql = XmlSQLMapper.receiveSql("userCatalog", "findUserById");
        return appJdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
    }

    @Override
    public User findUserByName(String name) {
        String sql = XmlSQLMapper.receiveSql("userCatalog", "findUserByName");
        List<User> list = appJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), name);
        return DataAccessUtils.uniqueResult(list);
    }
}
