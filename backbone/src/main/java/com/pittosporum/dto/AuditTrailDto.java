package com.pittosporum.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Setter
@Getter
public class AuditTrailDto {
    private Integer id;
    private Integer operation;
    private String module;
    private String functionName;
    private Integer relateTo;
    private String beforeData;
    private String afterData;
}
