package pittosporum.dto.view;

import lombok.Getter;
import lombok.Setter;
import net.sf.oval.constraint.MaxLength;
import net.sf.oval.constraint.MinLength;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;
import pittosporum.annotation.DisplayName;

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
