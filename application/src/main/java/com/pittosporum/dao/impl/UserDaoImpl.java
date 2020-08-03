package com.pittosporum.dao.impl;

import com.pittosporum.dao.RepositoryHelper;
import com.pittosporum.dao.UserDao;
import com.pittosporum.entity.User;
import com.pittosporum.xmlsql.XmlSQLMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private RepositoryHelper repositoryHelper;

    @Override
    public User findUserById(String id) {
        String sql = XmlSQLMapper.receiveSql("userCatalog", "findUserById");
        return repositoryHelper.queryForObject(sql, User.class, id);
    }

    @Override
    public User findUserByName(String name) {
        String sql = XmlSQLMapper.receiveSql("userCatalog", "findUserByName");
        List<User> list = repositoryHelper.queryForList(sql, User.class, name);
        return DataAccessUtils.uniqueResult(list);
    }
}
