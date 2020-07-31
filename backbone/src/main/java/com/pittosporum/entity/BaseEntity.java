package com.pittosporum.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Setter
@Getter
public class BaseEntity {

    private Date createDt;

    private String createBy;

    private Date updateDt;

    private String updateBy;

    private String status;
}
