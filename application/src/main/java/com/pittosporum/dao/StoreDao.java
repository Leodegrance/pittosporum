package com.pittosporum.dao;

import com.pittosporum.entity.SQLStore;

import java.util.List;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public interface StoreDao {
    List<SQLStore>  receiveSqlStore(String profileId, String status);
    void createStore(SQLStore store);
    SQLStore selectSqlStoreById(String id);
    void changeRunStatus(Integer id, String status);
    List<SQLStore> receiveStoreData();
}
