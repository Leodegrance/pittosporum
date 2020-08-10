package com.pittosporum.dao;

import com.pittosporum.entity.DataPatchLogging;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public interface DataPatchLoggingDao {
    void createDataPatchLogging(DataPatchLogging auditTrail);
}
