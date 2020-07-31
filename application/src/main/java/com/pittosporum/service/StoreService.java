package com.pittosporum.service;

import com.pittosporum.constant.ProcessResponse;
import com.pittosporum.dto.DataBaseProfileDto;
import com.pittosporum.dto.SQLStoreDto;
import com.pittosporum.dto.view.QueryParam;
import com.pittosporum.dto.view.QueryResult;
import com.pittosporum.dto.view.SQLStoreQueryDto;

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
