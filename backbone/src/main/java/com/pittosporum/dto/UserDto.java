package com.pittosporum.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Setter
@Getter
public class UserDto {
    private Integer id;
    private String name;
    private String email;
    private Integer mobileNumber;
    private Date date;

    @JsonIgnore
    private String pwd;
}
