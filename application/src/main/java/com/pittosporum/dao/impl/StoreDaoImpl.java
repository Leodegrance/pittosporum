package com.pittosporum.dao.impl;

import com.pittosporum.constant.Status;
import com.pittosporum.dao.StoreDao;
import com.pittosporum.entity.SQLStore;
import com.pittosporum.utils.CommonUtil;
import com.pittosporum.xmlsql.XmlSQLMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Repository
public class StoreDaoImpl implements StoreDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<SQLStore> receiveSqlStore(Integer profileId, String status) {
        String sql = XmlSQLMapper.receiveSql("storeCatalog", "receiveSqlStore");
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SQLStore.class));
    }

    @Override
    public void createStore(SQLStore store) {
        String sql = XmlSQLMapper.receiveSql("storeCatalog", "createStore");
        jdbcTemplate.update(sql, new Object[] {store.getExecuteSql(), Status.PENDING_EXECUTE, store.getProfileId(), "graffitidef", new Date(), "graffitidef", new Date(), Status.ACTIVE_RECORD});
    }

    @Override
    public SQLStore selectSqlStoreById(Integer id) {
        String sql = XmlSQLMapper.receiveSql("storeCatalog", "selectSqlStoreById");
        List<SQLStore> sqlStoreList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SQLStore.class), id);
        return CommonUtil.isEmpty(sqlStoreList) ? null : sqlStoreList.get(0);
    }

    @Override
    public void changeRunStatus(Integer id, String status) {
        String runCountSql = XmlSQLMapper.receiveSql("storeCatalog", "runCountStore");
        int count = jdbcTemplate.queryForObject(runCountSql, Integer.TYPE, id);
        String sql = XmlSQLMapper.receiveSql("storeCatalog", "changeRunStatus");
        jdbcTemplate.update(sql,new Object[]{status, count + 1, id});
    }

    @Override
    public List<SQLStore> selectSqlByDate(Date date) {

        return null;
    }
}
