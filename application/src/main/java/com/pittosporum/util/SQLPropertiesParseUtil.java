package com.pittosporum.util;

import com.pittosporum.constant.GeneralConstant;
import com.pittosporum.core.SQLProperties;
import com.pittosporum.core.SQLStoreFactory;
import com.pittosporum.entity.SQLStore;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.PriorityQueue;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Slf4j
public class SQLPropertiesParseUtil {
    private SQLPropertiesParseUtil(){}

    public static PriorityQueue<SQLProperties> parseToSQLPropertiesList(List<SQLStore> storeList){
        PriorityQueue<SQLProperties> priorityQueue = new PriorityQueue<>();
        for (SQLStore i : storeList){
            SQLProperties e = parseToSQLProperties(i);
            if (e != null){
                priorityQueue.add(e);
            }
        }
        return priorityQueue;
    }

    public static SQLProperties parseToSQLProperties(SQLStore sqlStore){
        if (sqlStore == null){
            return null;
        }

        int profileId = sqlStore.getProfileId();
        int storeId = sqlStore.getId();
        String executeSql = sqlStore.getExecuteSql();
        String dmlName = executeSql.substring(0, 6);

        SQLProperties e = convertToProperties(executeSql, dmlName, profileId, storeId);
        return e;
    }

    private static SQLProperties convertToProperties(String executeSql, String dmlName, Integer profileId, Integer storeId){
        SQLProperties sqlProperties = SQLStoreFactory.createSQLProperties(dmlName);

        log.info("parse sql sqlProperties ", sqlProperties);
        if (sqlProperties == null){
            return null;
        }else {
            String profileName = ProfileMapper.getProfileNameById(profileId);
            sqlProperties.setProfileId(profileId);
            sqlProperties.setProfileName(profileName);
            sqlProperties.setSql(executeSql);
            sqlProperties.setStoreId(storeId);
            return sqlProperties;
        }
    }

    public static void main(String[] args) {
        String sql = "INSERT INTO city (ID, Name, CountryCode, District, Population) VALUES(1, 'Kabul', 'AFG', 'Kabol', 1780000);";
        System.out.println(sql.substring(0, 6));
        String sql1 = sql.substring(0, 6);
        System.out.println(isUpdate(sql1));
    }


    private static boolean isInsert(String sql){
        return GeneralConstant.INSERT_LOWER.equals(sql) || GeneralConstant.INSERT_UPPER.equals(sql);
    }

    private static boolean isUpdate(String sql){
        return GeneralConstant.UPDATE_LOWER.equals(sql) || GeneralConstant.UPDATE_UPPER.equals(sql);
    }

    private static boolean isDelete(String sql){
        return GeneralConstant.DELETE_LOWER.equals(sql) || GeneralConstant.DELETE_UPPER.equals(sql);
    }
}
