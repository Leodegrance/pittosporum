package com.pittosporum.service.impl;

import com.pittosporum.constant.ProcessResponse;
import com.pittosporum.constant.Status;
import com.pittosporum.constant.app.AppErrorCode;
import com.pittosporum.core.SQLProperties;
import com.pittosporum.dao.StoreDao;
import com.pittosporum.entity.SQLStore;
import com.pittosporum.service.ExecuteService;
import com.pittosporum.util.SQLPropertiesParseUtil;
import com.pittosporum.utils.CommonUtil;
import com.pittosporum.utils.JDBCTemplateMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Service
@Slf4j
public class ExecuteServiceImpl implements ExecuteService {

    @Autowired
    private StoreDao dao;

    @Override
    @Transactional
    public ProcessResponse<Void> executeSqlByStoreId(Integer storeId) {
        SQLStore sqlStore = dao.selectSqlStoreById(storeId);
        if (sqlStore == null){
            log.info("====>>>>>> can not find sql by id", storeId);
            return ProcessResponse.failure(AppErrorCode.EMPTY_OBJECT.getStatusCode(), AppErrorCode.EMPTY_OBJECT.getMessage());
        }

        SQLProperties sqlProperties = SQLPropertiesParseUtil.parseToSQLProperties(sqlStore);
        execute(sqlProperties);
        return ProcessResponse.success();
    }

    @Override
    public ProcessResponse<Void> executeSqlList(List<Integer> storeIds) {
        List<SQLStore> sqlStoreList = new ArrayList<>();
        for (Integer s : storeIds){
            SQLStore e = dao.selectSqlStoreById(s);
            if (e != null){
                sqlStoreList.add(e);
            }
        }

        executeForList(sqlStoreList);

        return ProcessResponse.success();
    }

    private void executeForList(List<SQLStore> list){
        if (CommonUtil.isEmpty(list)){
            return;
        }

        PriorityQueue<SQLProperties> executeQueue = SQLPropertiesParseUtil.parseToSQLPropertiesList(list);

        Iterator<SQLProperties> itr = executeQueue.iterator();
        while (itr.hasNext()){
            SQLProperties e = itr.next();
            execute(e);
        }
    }

    private void execute(SQLProperties sqlProperties){
        if (sqlProperties == null){
            return;
        }

        try {
            String exSql = sqlProperties.getSql();
            String profileName = sqlProperties.getProfileName();

            JdbcTemplate jdbcTemplate = JDBCTemplateMapper.getJdbcTemplateByName(profileName);
            jdbcTemplate.update(exSql);
            dao.changeRunStatus(sqlProperties.getStoreId(), Status.EXECUTE_OVER);
        }catch (Exception e){
            log.error("========>>>>executeSqlByStoreId>>>>>>>>>>>>>", e);
            dao.changeRunStatus(sqlProperties.getStoreId(), Status.EXECUTE_FAILURE);
        }
    }

    @Override
    public ProcessResponse<Void> executeSqlByStatusAndDate(String status, Date date) {
        List<SQLStore> list = dao.selectSqlByStatusAndDate(status, date);

        executeForList(list);

        return ProcessResponse.success();
    }





}
