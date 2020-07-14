package com.pittosporum.entity;

import lombok.Getter;
import lombok.Setter;
import pittosporum.entity.BaseEntity;

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
}
