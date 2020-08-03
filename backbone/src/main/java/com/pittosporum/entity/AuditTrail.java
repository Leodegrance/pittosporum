package com.pittosporum.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Setter
@Getter
public class AuditTrail extends BaseEntity {
    private Integer id;
    private String adType;
    private String data;
    private Integer relateTo;
}
