package com.pittosporum.dao;

import pittosporum.dto.SQLStoreDto;
import com.pittosporum.entity.SQLStore;

import java.util.List;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public interface StoreDao {
    List<SQLStoreDto>  receiveSqlStore(String profileId, String status);
    void createStore(SQLStoreDto store);
    void createStoreList(List<SQLStoreDto> stores);
    SQLStore selectSqlStoreById(String id);
    void changeRunStatus(Integer id, String status);
}
