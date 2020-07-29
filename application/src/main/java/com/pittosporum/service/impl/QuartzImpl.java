package com.pittosporum.service.impl;

import com.pittosporum.dao.QuartzDao;
import com.pittosporum.entity.Quartz;
import com.pittosporum.service.QuartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pittosporum.constant.ProcessResponse;
import pittosporum.dao.QueryDao;
import pittosporum.dto.QuartzDto;
import pittosporum.dto.view.QuartzQueryDto;
import pittosporum.dto.view.QueryParam;
import pittosporum.dto.view.QueryResult;
import pittosporum.utils.BeanUtil;
import pittosporum.xmlsql.XmlSQLMapper;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Service
public class QuartzImpl implements QuartzService {

    @Autowired
    private QuartzDao quartzDao;

    @Autowired
    private QueryDao queryDao;

    @Override
    public ProcessResponse<Void> createQuartz(QuartzDto quartzDto) {
        Quartz quartz = BeanUtil.copyProperties(quartzDto, Quartz.class);
        quartzDao.createQuartz(quartz);
        return ProcessResponse.success();
    }

    @Override
    public ProcessResponse<Void> startJob(Integer jobId) {
        return null;
    }

    @Override
    public ProcessResponse<Void> deleteQuartz(Integer jobId) {
        return null;
    }

    @Override
    public QueryResult<QuartzQueryDto> receiveJobList(QueryParam queryParam) {
        String sql = XmlSQLMapper.receiveSql("quartzCatalog", "receiveAll");
        queryParam.setEntityClz(QuartzQueryDto.class);
        queryParam.setMainSql(sql);
        return queryDao.query(queryParam);
    }
}
