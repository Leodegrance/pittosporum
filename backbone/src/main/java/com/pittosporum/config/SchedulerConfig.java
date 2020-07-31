package com.pittosporum.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Configuration
public class SchedulerConfig {

    public SchedulerConfig(){
        System.out.println("==>>>>SchedulerConfig>>>>>>");
    }

    @Bean
    public Scheduler scheduler() throws SchedulerException {
        SchedulerFactory schedulerFactory  = new org.quartz.impl.StdSchedulerFactory();
        return schedulerFactory.getScheduler();
    }
}
