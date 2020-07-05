package com.pittosporum.util;

import com.pittosporum.entity.SynchronizationStore;
import lombok.extern.slf4j.Slf4j;
import pittosporum.constant.GeneralConstant;
import pittosporum.core.ProfileMapper;
import pittosporum.core.SQLProperties;
import pittosporum.core.SQLStoreFactory;
import pittosporum.utils.CommonUtil;

import java.util.List;
import java.util.PriorityQueue;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Slf4j
public class SqlParseUtil {
    private SqlParseUtil(){}

    private static PriorityQueue<SQLProperties> parseToSQLPropertiesList(List<SynchronizationStore> storeList){
        if (CommonUtil.isEmpty(storeList)){
            return null;
        }

        PriorityQueue<SQLProperties> priorityQueue = new PriorityQueue<>();
        for (SynchronizationStore i : storeList){
            int profileId = i.getProfileId();
            String executeSql = i.getExecuteSql();
            String dmlName = executeSql.substring(0, 6);

            SQLProperties sqlProperties = null;
            if (isDelete(dmlName)){
                sqlProperties = SQLStoreFactory.createSQLProperties(dmlName);
            }else if (isUpdate(dmlName)){
                sqlProperties = SQLStoreFactory.createSQLProperties(dmlName);
            }else if (isInsert(dmlName)){
                sqlProperties = SQLStoreFactory.createSQLProperties(dmlName);
            }

            log.info("parse sql sqlProperties ", sqlProperties);
            if (sqlProperties != null){
                String profileName = ProfileMapper.getProfileNameById(profileId);
                sqlProperties.setProfileName(profileName);
                sqlProperties.setSql(executeSql);
                priorityQueue.add(sqlProperties);
            }
        }
        return priorityQueue;
    }

    //INSERT INTO world.city (ID, Name, CountryCode, District, Population) VALUES(1, 'Kabul', 'AFG', 'Kabol', 1780000);
    private SQLProperties convertToProperties(String executeSql){
        return null;
    }

    public static void main(String[] args) {
        String sql = "INSERT INTO city (ID, Name, CountryCode, District, Population) VALUES(1, 'Kabul', 'AFG', 'Kabol', 1780000);";
        System.out.println(sql.substring(0, 6));
        String sql1 = sql.substring(0, 6);
        System.out.println(isUpdate(sql1));
    }


    private static boolean isInsert(String sql){
        return GeneralConstant.INSERT_LOWER.equals(sql) || GeneralConstant.INSERT_LOWER.equals(sql);
    }

    private static boolean isUpdate(String sql){
        return GeneralConstant.UPDATE_LOWER.equals(sql) || GeneralConstant.UPDATE_UPPER.equals(sql);
    }

    private static boolean isDelete(String sql){
        return GeneralConstant.DELETE_LOWER.equals(sql) || GeneralConstant.DELETE_UPPER.equals(sql);
    }
}
