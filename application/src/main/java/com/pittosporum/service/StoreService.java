package com.pittosporum.service;

import pittosporum.constant.ProcessResponse;
import pittosporum.dto.SQLStoreDto;

import java.util.List;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public interface StoreService {
    List<SQLStoreDto> receiveSqlStore(String profileId, String status);

    ProcessResponse<Void> createStore(SQLStoreDto store);

    ProcessResponse<Void> createStoreList(List<SQLStoreDto> stores);

    List<SQLStoreDto> receiveStoreData();
}
