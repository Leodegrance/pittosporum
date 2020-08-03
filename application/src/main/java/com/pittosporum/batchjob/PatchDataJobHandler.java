package com.pittosporum.batchjob;

import com.pittosporum.batchjob.executor.JobHandler;
import com.pittosporum.constant.Status;
import com.pittosporum.service.ExecuteService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

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
        log.info("PatchDataJobHandler start.........");
        Date today = new Date();
        executeService.executeSqlByStatusAndDate(Status.PENDING_EXECUTE, today);
        log.info("PatchDataJobHandler end.........");
    }
}
