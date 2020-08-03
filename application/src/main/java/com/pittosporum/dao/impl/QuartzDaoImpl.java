package com.pittosporum.dao.impl;

import com.pittosporum.constant.Status;
import com.pittosporum.dao.QuartzDao;
import com.pittosporum.dao.RepositoryHelper;
import com.pittosporum.dto.view.QuartzQueryDto;
import com.pittosporum.dto.view.QueryParam;
import com.pittosporum.dto.view.QueryResult;
import com.pittosporum.entity.Quartz;
import com.pittosporum.xmlsql.XmlSQLMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Repository
public class QuartzDaoImpl implements QuartzDao {

    @Autowired
    private RepositoryHelper repositoryHelper;

    @Override
    public void createQuartz(Quartz quartz) {
        String sql = XmlSQLMapper.receiveSql("quartzCatalog", "createQuartz");
        repositoryHelper.update(sql, quartz.getJobName(), quartz.getJobGroup(), quartz.getStartTime(), quartz.getCronExp(), quartz.getInvokeParam(), Status.ACTIVE_RECORD);
    }

    @Override
    public Quartz getQuartzById(Integer id) {
        String sql = XmlSQLMapper.receiveSql("quartzCatalog", "getQuartz");
        return repositoryHelper.queryForObject(sql, Quartz.class, id);
    }

    @Override
    public QueryResult<QuartzQueryDto> receiveJobList(QueryParam queryParam) {
        return repositoryHelper.query(queryParam);
    }
}
