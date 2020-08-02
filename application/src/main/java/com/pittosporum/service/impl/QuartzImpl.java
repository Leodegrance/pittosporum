package com.pittosporum.service.impl;

import com.pittosporum.batchjob.JobHandlerMapper;
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
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

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
    private Scheduler scheduler;

    @PostConstruct
    public void startScheduler(){
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }

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

    @Override
    public ProcessResponse<QuartzDto> getQuartzById(Integer id) {
        Quartz quartz = quartzDao.getQuartzById(id);
        return ProcessResponse.success(BeanUtil.copyProperties(quartz, QuartzDto.class));
    }

    @Override
    public ProcessResponse<Void> runJob(Integer jobId) {
        Quartz quartz = quartzDao.getQuartzById(jobId);
        if (quartz == null){
            ProcessResponse.failure(AppErrorCode.RUN_JOB_FAILURE.getStatusCode(), AppErrorCode.RUN_JOB_FAILURE.getMessage());
        }

        String jobName = quartz.getJobName();
        String jobGroup = quartz.getJobGroup();
        String cronExp= quartz.getCronExp();

        Class clz = JobHandlerMapper.getClassByName(jobName);
        JobDetail job = newJob(clz).withIdentity(jobName, jobGroup).build();

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExp);
        Trigger trigger = newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(cronScheduleBuilder)
                .build();

        try {
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            return ProcessResponse.failure(AppErrorCode.RUN_JOB_FAILURE_2.getStatusCode(), AppErrorCode.RUN_JOB_FAILURE_2.getMessage());
        }

        return ProcessResponse.success();
    }
}
