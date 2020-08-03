package com.pittosporum.dao.impl;

import com.pittosporum.constant.Status;
import com.pittosporum.dao.RepositoryHelper;
import com.pittosporum.dao.StoreDao;
import com.pittosporum.dto.view.QueryParam;
import com.pittosporum.dto.view.QueryResult;
import com.pittosporum.dto.view.SQLStoreQueryDto;
import com.pittosporum.entity.SQLStore;
import com.pittosporum.xmlsql.XmlSQLMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Repository
public class StoreDaoImpl implements StoreDao {
    @Autowired
    private RepositoryHelper repositoryHelper;

    @Override
    public List<SQLStore> receiveSqlStore(Integer profileId, String status) {
        String sql = XmlSQLMapper.receiveSql("storeCatalog", "receiveSqlStore");
        return repositoryHelper.queryForList(sql, SQLStore.class);
    }

    @Override
    public void createStore(SQLStore store) {
        String sql = XmlSQLMapper.receiveSql("storeCatalog", "createStore");
        repositoryHelper.update(sql, new Object[] {store.getExecuteSql(), Status.PENDING_EXECUTE, store.getProfileId(), "graffitidef", new Date(), "graffitidef", new Date(), Status.ACTIVE_RECORD});
    }

    @Override
    public SQLStore selectSqlStoreById(Integer id) {
        String sql = XmlSQLMapper.receiveSql("storeCatalog", "selectSqlStoreById");
        return repositoryHelper.queryForObject(sql, SQLStore.class, id);
    }

    @Override
    public void changeRunStatus(Integer id, String status) {
        String runCountSql = XmlSQLMapper.receiveSql("storeCatalog", "runCountStore");
        int count = repositoryHelper.querySimpleObject(runCountSql, Integer.TYPE, id);
        String sql = XmlSQLMapper.receiveSql("storeCatalog", "changeRunStatus");
        repositoryHelper.update(sql, new Object[]{status, count + 1, id});
    }

    @Override
    public List<SQLStore> selectSqlByStatusAndDate(String status, Date date) {
        Map<String, Object> param = new HashMap<>();
        param.put("create_dt", date);
        param.put("execute_result", status);
        String sql = XmlSQLMapper.receiveSql("storeCatalog", "receiveSqlStore", param);
        return repositoryHelper.queryForList(sql, SQLStore.class, new Object[]{status, date});
    }

    @Override
    public QueryResult<SQLStoreQueryDto> receiveStoreData(QueryParam queryParam) {
        return repositoryHelper.query(queryParam);
    }
}
