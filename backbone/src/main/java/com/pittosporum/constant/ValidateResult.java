package com.pittosporum.constant;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Setter
@Getter
public class ValidateResult {
    private HashMap<String, String> errorMap;
    private boolean hasError = false;
}
