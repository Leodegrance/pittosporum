package com.pittosporum.service.impl;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.pittosporum.constant.ProcessResponse;
import com.pittosporum.constant.app.AppErrorCode;
import com.pittosporum.dao.StoreDao;
import com.pittosporum.dto.DataBaseProfileDto;
import com.pittosporum.dto.SQLStoreDto;
import com.pittosporum.dto.view.QueryParam;
import com.pittosporum.dto.view.QueryResult;
import com.pittosporum.dto.view.SQLStoreQueryDto;
import com.pittosporum.entity.DataBaseProfile;
import com.pittosporum.entity.SQLStore;
import com.pittosporum.service.StoreService;
import com.pittosporum.util.ProfileMapper;
import com.pittosporum.utils.BeanUtil;
import com.pittosporum.utils.CommonUtil;
import com.pittosporum.utils.SQLValidator;
import com.pittosporum.xmlsql.XmlSQLMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Service
@Slf4j
public class StoreServiceImpl implements StoreService {
    @Autowired
    private StoreDao storeDao;

    @Override
    public List<SQLStoreDto> receiveSqlStore(Integer profileId, String status) {
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
        int profileId = store.getProfileId();
        String executeSql = store.getExecuteSql();

        List<SQLStatement> statementList;
        try {
            statementList = SQLValidator.format(executeSql);
        } catch (Exception e) {
            return ProcessResponse.failure(AppErrorCode.FORMAT_SQL_ERROR.getStatusCode(), e.getMessage());
        }

        if (!CommonUtil.isEmpty(statementList)){
            for (SQLStatement statement : statementList){
                String parse = statement.toString();
                SQLStore sqlStore = new SQLStore();
                sqlStore.setProfileId(profileId);
                sqlStore.setExecuteSql(parse);
                storeDao.createStore(sqlStore);
            }
        }

        return ProcessResponse.success();
    }

    @Override
    public QueryResult<SQLStoreQueryDto> receiveStoreData(QueryParam queryParam) {
        String sql = XmlSQLMapper.receiveSql("storeCatalog", "receiveSqlStore");
        queryParam.setEntityClz(SQLStoreQueryDto.class);
        queryParam.setMainSql(sql);
        return storeDao.receiveStoreData(queryParam);
    }

    @Override
    public List<DataBaseProfileDto> receiveDataBaseProfile() {
        List<DataBaseProfileDto> result = new ArrayList<>();
        Map<Integer, DataBaseProfile> profileMap = ProfileMapper.profileMap;
        for (Map.Entry<Integer, DataBaseProfile> entry : profileMap.entrySet()){
            result.add(BeanUtil.copyProperties(entry.getValue(),DataBaseProfileDto.class));
        }
        return result;
    }
}
