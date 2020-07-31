package com.pittosporum.scheduler.core;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import com.pittosporum.exception.BaseRunException;
import com.pittosporum.scheduler.JobDelegator;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Slf4j
public class JobHandlerMapper {
    @Getter@Setter
    private static ThreadLocal<Set<Class<?>>> threadLocal = new ThreadLocal<>();
    private static Set<Class<?>> classSet = new HashSet<>();

    public static void scanClass(String pathName) throws ClassNotFoundException {
        if (StringUtils.isEmpty(pathName)){
            return;
        }

        File[] javaFileArray = listSchedulerFiles(pathName);
        if (javaFileArray == null || javaFileArray.length == 0){
            return;
        }

        for (File file : javaFileArray){
            Class<?> clz = convertFileToClass(file);
            if (isJobHandler(clz)){
                classSet.add(clz);
                threadLocal.set(classSet);
            }
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

    public static Class<?> convertFileToClass(File file) throws ClassNotFoundException {
        String fileName = file.getName();
        String[] className = fileName.split("\\.");

        Class<?> clz = Thread.currentThread().getContextClassLoader()
                .loadClass("com.com.pittosporum.scheduler." + className[0]);

        return clz;
    }

    public static File[] listSchedulerFiles(String pathName){
        File file = new File(pathName);
        if (!file.exists() || !file.isDirectory()){
            throw new BaseRunException("can not find directory" + pathName);
        }
        return file.listFiles();
    }
}
