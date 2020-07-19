package com.pittosporum.entity;

import lombok.Getter;
import lombok.Setter;
import pittosporum.dto.UserDto;
import pittosporum.entity.BaseEntity;
import pittosporum.utils.BeanUtil;

import java.util.Date;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Setter
@Getter
public class User extends BaseEntity {
    private Integer id;
    private String name;
    private String pwd;
    private String email;
    private Integer mobileNumber;
    private Date date;
}
