package com.pittosporum.dao;

import com.pittosporum.dto.view.QueryParam;
import com.pittosporum.dto.view.QueryResult;
import com.pittosporum.dto.view.SQLStoreQueryDto;
import com.pittosporum.entity.SQLStore;

import java.util.Date;
import java.util.List;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public interface StoreDao {
    List<SQLStore>  receiveSqlStore(Integer profileId, String status);

    void createStore(SQLStore store);

    void updateStore(SQLStore store);

    SQLStore selectSqlStoreById(Integer id);

    void changeRunStatus(Integer id, String status);

    void updateCause(Integer id, String cause);

    List<SQLStore> selectSqlByStatusAndDate(String status, Date date);

    QueryResult<SQLStoreQueryDto> receiveStoreData(QueryParam queryParam);
}
