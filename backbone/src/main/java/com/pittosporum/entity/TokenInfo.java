package com.pittosporum.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Getter
@Setter
public class TokenInfo {
    private String userName;
    private String tokenStr;
    private String secret;
}
