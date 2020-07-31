package com.pittosporum.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public class CommonUtil {
    private CommonUtil(){}
    public static <E> boolean isEmpty(Collection<E> collection){
        if (collection == null || collection.isEmpty()){
            return true;
        }else {
            return false;
        }
    }

    public static <K,V> boolean isEmpty(Map<K,V> map){
        if (map == null || map.isEmpty()){
            return true;
        }else {
            return false;
        }
    }

    public static boolean equalsClass(Object instance, Class<?> owner){
        if (instance == null || owner == null){
            return false;
        }

        Class<?> clz = instance.getClass();
        if (clz != owner){
            return false;
        }

        return true;
    }
}
