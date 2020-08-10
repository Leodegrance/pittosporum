package com.pittosporum.dao.impl;

import com.pittosporum.constant.Status;
import com.pittosporum.dao.DataPatchLoggingDao;
import com.pittosporum.dao.RepositoryHelper;
import com.pittosporum.entity.DataPatchLogging;
import com.pittosporum.xmlsql.XmlSQLMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Service
public class DataPatchLoggingImpl implements DataPatchLoggingDao {

    @Autowired
    private RepositoryHelper repositoryHelper;

    @Override
    public void createDataPatchLogging(DataPatchLogging dataPatchLogging) {
        String sql = XmlSQLMapper.receiveSql("dataPatchCatalog", "createDataPatchLogging");
        repositoryHelper.update(sql,
                new Object[]{dataPatchLogging.getOperation(), dataPatchLogging.getModule(), dataPatchLogging.getFunctionName(),
                        dataPatchLogging.getBeforeData(), dataPatchLogging.getRelateTo(), dataPatchLogging.getAfterData(), "yichen", new Date(), Status.ACTIVE_RECORD});

    }
}
