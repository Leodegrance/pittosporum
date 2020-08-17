package com.pittosporum.batchjob;

import com.pittosporum.utils.ResourceUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Slf4j
public class JobHandlerMapper {
    @Getter@Setter
    private static Set<Class<?>> classSet = new HashSet<>();

    public static void scanClass(String basePackage) throws ClassNotFoundException {
        if (StringUtils.isEmpty(basePackage)){
            return;
        }

        try {
            Set<Class<?>> set = ResourceUtil.searchClass(basePackage);
            for (Class clz : set){
                if (isJobHandler(clz)){
                    classSet.add(clz);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private static Boolean isJobHandler(Class<?> clz){
        if (clz == null){
            return Boolean.FALSE;
        }

        JobDelegator jobDelegator = clz.getAnnotation(JobDelegator.class);
        if (jobDelegator == null){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public static Class<?> getClassByName(String name){
        if (StringUtils.isEmpty(name)){
            return null;
        }

        if (classSet == null){
            return null;
        }

        for (Class<?> clz : classSet){
            JobDelegator jobDelegator = clz.getAnnotation(JobDelegator.class);
            if (jobDelegator != null){
                String val = jobDelegator.name();
                if (val.equals(name)){
                    return clz;
                }
            }
        }

        return null;
    }
}
