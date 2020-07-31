package com.pittosporum.dto;

import com.pittosporum.annotation.DisplayName;
import lombok.Getter;
import lombok.Setter;
import net.sf.oval.constraint.MaxLength;
import net.sf.oval.constraint.MinLength;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Setter
@Getter
public class QuartzDto {
    private Integer id;

    @NotNull(message = "can not is null", profiles = "create")
    @NotBlank(message = "can not is null", profiles = "create")
    @MaxLength(message = "field length cannot be greater than {&amp}", value = 25, profiles = "create")
    @MinLength(message = "field length cannot be less than {&amp}", value = 1, profiles = "create")
    @DisplayName(value = "Job name")
    private String jobName;

    @NotNull(message = "can not is null", profiles = "create")
    @NotBlank(message = "can not is null", profiles = "create")
    @MaxLength(message = "field length cannot be greater than {&amp}", value = 25, profiles = "create")
    @MinLength(message = "field length cannot be less than {&amp}", value = 1, profiles = "create")
    @DisplayName(value = "Job group")
    private String jobGroup;

    private String startTime;

    @NotNull(message = "can not is null", profiles = "create")
    @NotBlank(message = "can not is null", profiles = "create")
    @MaxLength(message = "field length cannot be greater than {&amp}", value = 25, profiles = "create")
    @MinLength(message = "field length cannot be less than {&amp}", value = 1, profiles = "create")
    @DisplayName(value = "Cron schedule")
    private String cronExp;

    private String invokeParam;
}
