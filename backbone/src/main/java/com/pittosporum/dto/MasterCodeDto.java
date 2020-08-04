package com.pittosporum.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Getter
@Setter
public class MasterCodeDto {
    private Integer id;
    private String codeKey;
    private String description;
    private String codeValue;
    private Integer codeCategoryId;
}
