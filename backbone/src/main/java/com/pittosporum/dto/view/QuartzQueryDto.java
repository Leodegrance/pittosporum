package com.pittosporum.dto.view;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Getter
@Setter
public class QuartzQueryDto {
    private Integer id;
    private String jobName;
    private String jobGroup;
    private String startTime;
    private String cronSchedule;
    private String invokeParam;
}
