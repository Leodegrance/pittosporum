package com.pittosporum.batchjob;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.stereotype.Component;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public class JobDataHelper {
    private JobDataHelper(){}

    public static JobDataMap getJobDataMap(JobDetail jobDetail){
        if (jobDetail == null){
            return null;
        }

        return jobDetail.getJobDataMap();
    }
}
