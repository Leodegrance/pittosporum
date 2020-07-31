package com.pittosporum.batchjob;

import com.pittosporum.batchjob.executor.JobHandler;
import com.pittosporum.service.ExecuteService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Slf4j
@DisallowConcurrentExecution
@JobDelegator(name = "patchDataJobHandler")
public class PatchDataJobHandler implements JobHandler {
    @Autowired
    private ExecuteService executeService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        String nowTime = sdf.format(new Date());
        List<Integer> integerList = new ArrayList<>();
        for (int i = 10009; i < 10027; i++){
            integerList.add(i);
        }
        executeService.executeSqlList(integerList);

        log.info("PatchDataJobHandler -- Time：" + nowTime);
    }

    public static void main(String[] args) {
        SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
        Scheduler sched = null;
        try {
            sched = schedFact.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        try {
            sched.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        // define the job and tie it to our
        JobDetail  job = newJob(PatchDataJobHandler.class)
                .withIdentity("myJob", "group1")
                .build();
        //Trigger the job to run now, andthen every 40 seconds
        Trigger trigger = newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(40)
                        .repeatForever())
                .build();
        //Tell quartz to schedule the job using our trigger
        try {
            sched.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
