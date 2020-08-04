package com.pittosporum.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Getter
@Setter
public class MasterCode extends BaseEntity{
    private Integer id;
    private String codeKey;
    private String description;
    private String codeValue;
    private Integer codeCategoryId;
}
