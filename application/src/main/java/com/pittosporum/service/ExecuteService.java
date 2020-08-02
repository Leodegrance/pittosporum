package com.pittosporum.service;

import com.pittosporum.constant.ProcessResponse;

import java.util.Date;
import java.util.List;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public interface ExecuteService {

    ProcessResponse<Void> executeSqlByStoreId(Integer storeId);

    ProcessResponse<Void> executeSqlList(List<Integer> storeIds);

    ProcessResponse<Void> executeSqlByDate(Date date);


}
