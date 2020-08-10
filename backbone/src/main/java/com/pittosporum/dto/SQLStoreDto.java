package com.pittosporum.dto;

import lombok.Getter;
import lombok.Setter;
import net.sf.oval.constraint.MaxLength;
import net.sf.oval.constraint.MinLength;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Getter
@Setter
public class SQLStoreDto implements Serializable{
    private static final long serialVersionUID = -231417890577521615L;

    private Integer id;

    @NotNull(message = "can not is null", profiles = "create")
    @NotBlank(message = "can not is null", profiles = "create")
    @MaxLength(message = "field length cannot be greater than {&amp}", value = 20000, profiles = "create")
    @MinLength(message = "field length cannot be less than {&amp}", value = 1, profiles = "create")
    private String executeSql;

    private String executeResult;

    private String profileName;

/*    @Max(message = "field value cannot be greater than {&amp}", value = 100)
    @Min(message = "field value cannot be greater than {&amp}", value = 1)*/
    @NotNull(message = "can not is null", profiles = "create")
    @NotBlank(message = "can not is null", profiles = "create")
    private Integer profileId;

/*    @NotNull(message = "can not is null", profiles = "create")
    @NotBlank(message = "can not is null", profiles = "create")*/
    private String createBy;

    private String createDt;

    private String status;

    private Integer runCount;

    private String cause;
}
