package com.pittosporum.service.impl;

import com.pittosporum.dao.StoreDao;
import com.pittosporum.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pittosporum.core.ProcessResponse;
import pittosporum.dto.SQLStoreDto;

import java.util.List;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    private StoreDao storeDao;

    @Override
    public List<SQLStoreDto> receiveSqlStore(String profileId, String status) {
        return storeDao.receiveSqlStore(profileId, status);
    }

    @Override
    @Transactional
    public ProcessResponse<Void> createStore(SQLStoreDto store) {
        storeDao.createStore(store);
        return ProcessResponse.success();
    }

    @Override
    @Transactional
    public ProcessResponse<Void> createStoreList(List<SQLStoreDto> stores) {
        storeDao.createStoreList(stores);
        return ProcessResponse.success();
    }
}
