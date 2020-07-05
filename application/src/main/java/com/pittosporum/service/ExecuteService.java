package com.pittosporum.service;

import pittosporum.core.ProcessResponse;

import java.util.List;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public interface ExecuteService {

    ProcessResponse<Void> executeSqlByStoreId(String storeId);

    void executeSqlList(List<String> storeIds);
}
