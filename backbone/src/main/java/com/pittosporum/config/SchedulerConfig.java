package com.pittosporum.config;

import com.pittosporum.batchjob.JobFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Configuration
public class SchedulerConfig {

    @Autowired
    private JobFactory jobFactory;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(jobFactory);
        return schedulerFactoryBean;
    }

    @Bean
    public JobFactory jobFactory(){
        return new JobFactory();
    }

    @Bean
    public Scheduler scheduler(){
        return schedulerFactoryBean.getScheduler();
    }
}
