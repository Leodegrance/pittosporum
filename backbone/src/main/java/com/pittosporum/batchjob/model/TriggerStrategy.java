package com.pittosporum.batchjob.model;

import lombok.Getter;
import lombok.Setter;
import org.quartz.Scheduler;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Getter
@Setter
public class TriggerStrategy {
    private String jobName;
    private String jobGroup;
    private String triggerName;
    private String triggerGroup;
    private String cronExp;
    private Class<?> jobClz;
    private Scheduler scheduler;
}
