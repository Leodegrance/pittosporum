package com.pittosporum.service.impl;

import com.pittosporum.constant.DataPatchLoggingConstant;
import com.pittosporum.constant.ProcessResponse;
import com.pittosporum.constant.Status;
import com.pittosporum.constant.app.AppErrorCode;
import com.pittosporum.core.Delete;
import com.pittosporum.core.Insert;
import com.pittosporum.core.SQLProperties;
import com.pittosporum.core.Update;
import com.pittosporum.dao.DataPatchLoggingDao;
import com.pittosporum.dao.StoreDao;
import com.pittosporum.entity.DataPatchLogging;
import com.pittosporum.entity.SQLStore;
import com.pittosporum.service.ExecuteService;
import com.pittosporum.util.SQLPropertiesParseUtil;
import com.pittosporum.utils.CommonUtil;
import com.pittosporum.utils.JDBCTemplateMapper;
import com.pittosporum.utils.JsonUtil;
import com.pittosporum.utils.SQLValidator;
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

    @Autowired
    private DataPatchLoggingDao dataPatchLoggingDao;

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

        DataPatchLogging logging = null;
        try {
            String exSql = sqlProperties.getSql();
            String profileName = sqlProperties.getProfileName();
            JdbcTemplate jdbcTemplate = JDBCTemplateMapper.getJdbcTemplateByName(profileName);
            String backupSql = getBackupSql(exSql);
            List<Map<String, Object>> backupResult = jdbcTemplate.queryForList(backupSql);

            logging = new DataPatchLogging();
            int operation;
            if (sqlProperties instanceof Delete){
                logging.setBeforeData(JsonUtil.parseToJson(backupResult));
                operation = DataPatchLoggingConstant.OPERATION_DELETE;
            }else if (sqlProperties instanceof Insert){
                logging.setAfterData(exSql);
                operation = DataPatchLoggingConstant.OPERATION_INSERT;
            }else if (sqlProperties instanceof Update){
                logging.setBeforeData(JsonUtil.parseToJson(backupResult));
                operation = DataPatchLoggingConstant.OPERATION_UPDATE;
            }else {
                operation = DataPatchLoggingConstant.OPERATION_QUERY;
            }

            logging.setRelateTo(sqlProperties.getStoreId());
            logging.setAfterData(exSql);
            logging.setFunctionName("execute service");
            logging.setOperation(operation);
            logging.setModule("application");
            logging.setStatus(Status.ACTIVE_RECORD);

            jdbcTemplate.update(exSql);
            dao.changeRunStatus(sqlProperties.getStoreId(), Status.EXECUTE_OVER);
            dao.updateCause(sqlProperties.getStoreId(), null);
        }catch (Exception e){
            log.error(e.getMessage());
            dao.changeRunStatus(sqlProperties.getStoreId(), Status.EXECUTE_FAILURE);
            dao.updateCause(sqlProperties.getStoreId(), e.getMessage());
        }finally {
            if (logging != null){
                dataPatchLoggingDao.createDataPatchLogging(logging);
            }
        }
    }

    @Override
    public ProcessResponse<Void> executeSqlByStatusAndDate(String status, Date date) {
        List<SQLStore> list = dao.selectSqlByStatusAndDate(status, date);

        executeForList(list);

        return ProcessResponse.success();
    }

    public String getBackupSql(String exSql){
        StringBuilder stb = new StringBuilder();
        String tableName = SQLValidator.getTableName(exSql);
        stb.append("select * from ").append(tableName).append(" ");
        String subConditions = SQLValidator.subConditions(exSql);
        stb.append(subConditions);
        return stb.toString();
    }
}
