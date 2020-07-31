package com.pittosporum.service.impl;

import com.pittosporum.batchjob.JobHandlerMapper;
import com.pittosporum.batchjob.executor.JobExecutor;
import com.pittosporum.batchjob.model.TriggerStrategy;
import com.pittosporum.constant.ProcessResponse;
import com.pittosporum.constant.app.AppErrorCode;
import com.pittosporum.dao.QuartzDao;
import com.pittosporum.dao.QueryDao;
import com.pittosporum.dto.QuartzDto;
import com.pittosporum.dto.view.QuartzQueryDto;
import com.pittosporum.dto.view.QueryParam;
import com.pittosporum.dto.view.QueryResult;
import com.pittosporum.entity.Quartz;
import com.pittosporum.service.QuartzService;
import com.pittosporum.utils.BeanUtil;
import com.pittosporum.xmlsql.XmlSQLMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Service
@Slf4j
public class QuartzImpl implements QuartzService {

    @Autowired
    private QuartzDao quartzDao;

    @Autowired
    private QueryDao queryDao;

    @Autowired
    private JobExecutor jobExecutor;



    @Override
    public ProcessResponse<Void> createQuartz(QuartzDto quartzDto) {
        Quartz quartz = BeanUtil.copyProperties(quartzDto, Quartz.class);
        quartzDao.createQuartz(quartz);
        return ProcessResponse.success();
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

    @Override
    public ProcessResponse<QuartzDto> getQuartzById(Integer id) {
        Quartz quartz = quartzDao.getQuartzById(id);
        return ProcessResponse.success(BeanUtil.copyProperties(quartz, QuartzDto.class));
    }

    @Override
    public ProcessResponse<Void> startJob(Integer jobId) {
        Quartz quartz = quartzDao.getQuartzById(jobId);
        if (quartz == null){
            ProcessResponse.failure(AppErrorCode.RUN_JOB_FAILURE.getStatusCode(), AppErrorCode.RUN_JOB_FAILURE.getMessage());
        }

        String jobName = quartz.getJobName();
        String jobGroup = quartz.getJobGroup();
        String cronExp= quartz.getCronExp();

        Class clz = JobHandlerMapper.getClassByName(jobName);
        TriggerStrategy triggerStrategy = new TriggerStrategy();
        triggerStrategy.setJobName(jobName);
        triggerStrategy.setJobGroup(jobGroup);
        triggerStrategy.setCronExp(cronExp);
        triggerStrategy.setJobClz(clz);
        jobExecutor.execute(triggerStrategy);
        return ProcessResponse.success();
    }
}
