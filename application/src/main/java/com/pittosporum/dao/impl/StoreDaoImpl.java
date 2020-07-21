package com.pittosporum.dao.impl;

import com.pittosporum.dao.StoreDao;
import com.pittosporum.entity.SQLStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pittosporum.constant.Status;
import pittosporum.dto.SQLStoreDto;
import pittosporum.utils.CommonUtil;
import pittosporum.xmlsql.XmlSQLMapper;

import java.util.Date;
import java.util.List;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Service
public class StoreDaoImpl implements StoreDao {
    @Autowired
    @Qualifier("appJdbcTemplate")
    private JdbcTemplate appJdbcTemplate;

    @Override
    public List<SQLStore> receiveSqlStore(String profileId, String status) {
        String sql = XmlSQLMapper.receiveSql("storeCatalog", "receiveSqlStore");
        return appJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SQLStore.class));
    }

    @Override
    public void createStore(SQLStoreDto store) {
        String sql = XmlSQLMapper.receiveSql("storeCatalog", "insertSynchronizationStore");
        appJdbcTemplate.update(sql, new Object[] {store.getExecuteSql(), store.getExecuteResult(), store.getProfileId(), "graffitidef", new Date(), "graffitidef", new Date(), Status.ACTIVE_RECORD});
    }

    @Override
    public void createStoreList(List<SQLStoreDto> stores) {
       String sql = XmlSQLMapper.receiveSql("storeCatalog", "createStore");
       for (SQLStoreDto store : stores){
           appJdbcTemplate.update(sql, new Object[] {store.getExecuteSql(), store.getExecuteResult(), store.getProfileId(), "graffitidef", new Date(), "graffitidef", new Date(), Status.ACTIVE_RECORD});
       }
    }

    @Override
    public SQLStore selectSqlStoreById(String id) {
        String sql = XmlSQLMapper.receiveSql("storeCatalog", "selectSqlStoreById");
        List<SQLStore> sqlStoreList = appJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SQLStore.class), id);
        return CommonUtil.isEmpty(sqlStoreList) ? null : sqlStoreList.get(0);
    }

    @Override
    public void changeRunStatus(Integer id, String status) {
        String runCountSql = XmlSQLMapper.receiveSql("storeCatalog", "runCountStore");
        int count = appJdbcTemplate.queryForObject(runCountSql, Integer.TYPE, id);
        String sql = XmlSQLMapper.receiveSql("storeCatalog", "changeRunStatus");
        appJdbcTemplate.update(sql,new Object[]{status, count + 1, id});
    }

    @Override
    public List<SQLStore> receiveStoreData() {
        String sql = XmlSQLMapper.receiveSql("storeCatalog", "receiveSqlStore");
        return appJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SQLStore.class));
    }
}
