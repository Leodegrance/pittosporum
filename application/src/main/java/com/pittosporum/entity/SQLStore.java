package com.pittosporum.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Getter
@Setter
public class SQLStore extends BaseEntity {
    private Integer id;
    private String executeSql;
    private String executeResult;
    private Integer profileId;
    private Integer runCount;
    private String cause;
}
