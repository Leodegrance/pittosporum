package com.pittosporum.batchjob.executor;

import com.pittosporum.batchjob.model.TriggerStrategy;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Component
@Slf4j
public class JobExecutor implements IJobExecutor{
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
    public void execute(TriggerStrategy triggerStrategy) {
        String jobName = triggerStrategy.getJobName();
        String jobGroup = triggerStrategy.getJobGroup();
        String cronExp= triggerStrategy.getCronExp();
        Class clz = triggerStrategy.getJobClz();

        JobDetail job = newJob(clz).withIdentity(jobName, jobGroup).build();

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExp);
        Trigger trigger = newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(cronScheduleBuilder)
                .build();

        try {
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            if (Trigger.TriggerState.NONE.equals(triggerState)){
                scheduler.scheduleJob(job, trigger);
            }
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }
}
