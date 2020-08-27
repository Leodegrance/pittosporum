package com.pittosporum.annotation;

import java.lang.annotation.*;

/**
 * @author yichen(graffitidef @ gmail.com)
 */
@Documented
@Retention(value = RetentionPolicy.SOURCE)
@Target(value = ElementType.TYPE)
public @interface ThreadSafe {
}
