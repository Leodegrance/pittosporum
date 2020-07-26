package com.pittosporum.service;

import pittosporum.constant.ProcessResponse;
import pittosporum.dto.DataBaseProfileDto;
import pittosporum.dto.SQLStoreDto;
import pittosporum.dto.view.QueryParam;
import pittosporum.dto.view.QueryResult;
import pittosporum.dto.view.SQLStoreQueryDto;

import java.util.List;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public interface StoreService {
    List<SQLStoreDto> receiveSqlStore(String profileId, String status);

    ProcessResponse<Void> createStore(SQLStoreDto store);

    QueryResult<SQLStoreQueryDto> receiveStoreData(QueryParam queryParam);

    List<DataBaseProfileDto> receiveDataBaseProfile();
}
