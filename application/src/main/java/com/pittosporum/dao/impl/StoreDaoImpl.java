package com.pittosporum.dao.impl;

import com.pittosporum.dao.StoreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pittosporum.constant.Status;
import pittosporum.dto.SQLStoreDto;
import pittosporum.entity.SQLStore;

import java.util.Date;
import java.util.List;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Service
public class StoreDaoImpl implements StoreDao {
    @Autowired
    @Qualifier("oRumJdbcTemplate")
    private JdbcTemplate oRumJdbcTemplate;

    @Override
    public List<SQLStoreDto> receiveSqlStore(String profileId, String status) {
        String sql = "SELECT id, execute_sql, execute_result, profile_id, create_by, create_dt, update_by, update_dt, status FROM synchronization_store";
        return oRumJdbcTemplate.query(sql,new BeanPropertyRowMapper<>(SQLStoreDto.class));
    }

    @Override
    public void createStore(SQLStoreDto store) {
        String sql = "INSERT INTO synchronization_store (execute_sql, execute_result, profile_id, create_by, create_dt, update_by, update_dt, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        oRumJdbcTemplate.update(sql, new Object[] {store.getExecuteSql(), store.getExecuteResult(), store.getProfileId(), "graffitidef", new Date(), "graffitidef", new Date(), Status.ACTIVE_RECORD});
    }

    @Override
    public void createStoreList(List<SQLStoreDto> stores) {
       String sql = "INSERT INTO synchronization_store (execute_sql, execute_result, profile_id, create_by, create_dt, update_by, update_dt, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
       for (SQLStoreDto store : stores){
           oRumJdbcTemplate.update(sql, new Object[] {store.getExecuteSql(), store.getExecuteResult(), store.getProfileId(), "graffitidef", new Date(), "graffitidef", new Date(), Status.ACTIVE_RECORD});
       }
    }

    @Override
    public SQLStore selectSqlStoreById(String id) {
        String sql = "SELECT id, execute_sql, execute_result, profile_id, create_by, create_dt, update_by, update_dt, status FROM synchronization_store where id = ?";
        SQLStore store = oRumJdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(SQLStore.class), id);
        return store;
    }
}
