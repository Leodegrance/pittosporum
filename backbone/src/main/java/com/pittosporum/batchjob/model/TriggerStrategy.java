package com.pittosporum.batchjob.model;

import org.quartz.ScheduleBuilder;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public class TriggerStrategy {
    private String triggerName;
    private String triggerGroup;
    private ScheduleBuilder scheduleBuilder;
}
