package com.pittosporum.entity;

import lombok.Getter;
import lombok.Setter;
import pittosporum.entity.BaseEntity;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Setter
@Getter
public class User extends BaseEntity {
    Integer id;
    String name;
    String pwd;
    String email;
    Integer mobileNumber;
}
