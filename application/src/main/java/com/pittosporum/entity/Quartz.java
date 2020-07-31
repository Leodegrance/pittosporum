package com.pittosporum.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Getter
@Setter
public class Quartz extends BaseEntity {
    private Integer id;
    private String jobName;
    private String jobGroup;
    private String startTime;
    private String cronExp;
    private String invokeParam;
}
