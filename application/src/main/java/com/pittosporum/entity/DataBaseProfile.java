package com.pittosporum.entity;

import lombok.Getter;
import lombok.Setter;
import com.pittosporum.core.SQLProperties;
import pittosporum.entity.BaseEntity;

import java.util.List;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Setter
@Getter
public class DataBaseProfile extends BaseEntity {
    private Integer id;
    private String profileName;

    private List<SQLProperties> sqlProperties;
}
