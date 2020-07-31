package com.pittosporum.batchjob;


import java.lang.annotation.*;

@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface JobDelegator {
    String name();
}
