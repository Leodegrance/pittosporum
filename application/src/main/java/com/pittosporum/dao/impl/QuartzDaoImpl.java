package com.pittosporum.dao.impl;

import com.pittosporum.constant.Status;
import com.pittosporum.dao.QuartzDao;
import com.pittosporum.entity.Quartz;
import com.pittosporum.utils.CommonUtil;
import com.pittosporum.xmlsql.XmlSQLMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        jdbcTemplate.update(sql, quartz.getJobName(), quartz.getJobGroup(), quartz.getStartTime(), quartz.getCronExp(), quartz.getInvokeParam(), Status.ACTIVE_RECORD);
    }

    @Override
    public Quartz getQuartzById(Integer id) {
        String sql = XmlSQLMapper.receiveSql("quartzCatalog", "getQuartz");
        List<Quartz> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Quartz.class), id);
        return CommonUtil.isEmpty(list) ? null : list.get(0);
    }
}
