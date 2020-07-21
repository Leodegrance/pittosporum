package com.pittosporum.service.impl;

import com.pittosporum.dao.StoreDao;
import com.pittosporum.entity.SQLStore;
import com.pittosporum.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pittosporum.constant.ProcessResponse;
import pittosporum.dto.SQLStoreDto;
import pittosporum.utils.BeanUtil;

import java.util.ArrayList;
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
        List<SQLStoreDto> result = new ArrayList<>();
        List<SQLStore> list = storeDao.receiveSqlStore(profileId, status);
        for (SQLStore i : list){
            result.add(BeanUtil.copyProperties(i, SQLStoreDto.class));
        }
        return result;
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

    @Override
    public List<SQLStoreDto> receiveStoreData() {
        List<SQLStore> list = storeDao.receiveStoreData();
        return BeanUtil.copyPropertiesToList(list, SQLStoreDto.class);
    }
}
