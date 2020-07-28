package pittosporum.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Setter
@Getter
public class QuartzDto {
    private Integer id;
    private String jobName;
    private String jobGroup;
    private String startTime;
    private String cronSchedule;
    private String invokeParam;
}
