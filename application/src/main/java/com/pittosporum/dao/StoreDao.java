package com.pittosporum.dao;

import com.pittosporum.entity.SQLStore;

import java.util.Date;
import java.util.List;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public interface StoreDao {
    List<SQLStore>  receiveSqlStore(Integer profileId, String status);

    void createStore(SQLStore store);

    SQLStore selectSqlStoreById(Integer id);

    void changeRunStatus(Integer id, String status);

    List<SQLStore> selectSqlByDate(Date date);
}
