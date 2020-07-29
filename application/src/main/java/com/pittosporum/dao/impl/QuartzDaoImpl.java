package com.pittosporum.dao.impl;

import com.pittosporum.dao.QuartzDao;
import com.pittosporum.entity.Quartz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pittosporum.constant.Status;
import pittosporum.xmlsql.XmlSQLMapper;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Repository
public class QuartzDaoImpl implements QuartzDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void createQuartz(Quartz quartz) {
        String sql = XmlSQLMapper.receiveSql("quartzCatalog", "createQuartz");
        jdbcTemplate.update(sql, quartz.getJobName(), quartz.getJobGroup(), quartz.getStartTime(), quartz.getCronSchedule(), quartz.getInvokeParam(), Status.ACTIVE_RECORD);
    }
}
