package com.pittosporum.dao;

import pittosporum.dto.SQLStoreDto;
import pittosporum.entity.SQLStore;

import java.util.List;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public interface StoreDao {
    List<SQLStoreDto>  receiveSqlStore(String profileId, String status);
    void createStore(SQLStoreDto store);
    void createStoreList(List<SQLStoreDto> stores);
    SQLStore selectSqlStoreById(String id);
    void changeRunStatus(String id, String status);
}
