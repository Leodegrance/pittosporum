package com.pittosporum.service;

import pittosporum.constant.ProcessResponse;
import pittosporum.dto.DataBaseProfileDto;
import pittosporum.dto.SQLStoreDto;

import java.util.List;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public interface StoreService {
    List<SQLStoreDto> receiveSqlStore(String profileId, String status);

    ProcessResponse<Void> createStore(SQLStoreDto store);

    List<SQLStoreDto> receiveStoreData();

    List<DataBaseProfileDto> receiveDataBaseProfile();
}
