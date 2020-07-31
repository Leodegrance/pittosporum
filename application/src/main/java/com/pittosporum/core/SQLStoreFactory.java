package com.pittosporum.core;

import com.pittosporum.constant.GeneralConstant;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public class SQLStoreFactory{
    public static SQLProperties createSQLProperties(String dmlName) {
        if(dmlName.equalsIgnoreCase(GeneralConstant.INSERT_UPPER)){
            return new Insert();
        }
        else if(dmlName.equalsIgnoreCase(GeneralConstant.UPDATE_UPPER)) {
            return new Update();
        }else if (dmlName.equalsIgnoreCase(GeneralConstant.DELETE_UPPER)){
            return new Delete();
        }
        return null;
    }
}
