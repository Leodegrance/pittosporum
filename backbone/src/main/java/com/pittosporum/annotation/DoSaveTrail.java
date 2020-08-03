package com.pittosporum.annotation;

import java.lang.annotation.*;

/**
 * @author yichen(graffitidef @ gmail.com)
 */
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface DoSaveTrail {
    String operation();
    String moduleName();
    String functionName();
}
