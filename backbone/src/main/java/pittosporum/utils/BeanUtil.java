package pittosporum.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Slf4j
public class BeanUtil{
    /**
     * convert bean properties
     * @param instance
     * @param targetCls
     * @param <T>
     * @return
     */
    public static <T> T copyProperties(Object instance, Class<?> targetCls) {
        if (instance == null || targetCls == null){
            return null;
        }

        Object targetObj = null;
        try {
            targetObj = targetCls.newInstance();

            HashMap<String, Object> valMapper = new HashMap<>();
            Class fromClz = instance.getClass();
            Field[] fields = fromClz.getDeclaredFields();
            for (Field field : fields){
                Class<?> fieldType = field.getType();
                String fieldName = field.getName();
                String methodName;
                if (boolean.class == fieldType){
                    methodName = "is" + StringUtils.capitalize(fieldName);
                }else {
                    methodName = "get" + StringUtils.capitalize(fieldName);
                }

                Object value = getObjectValue(instance, methodName);
                if (value != null){
                    valMapper.put(fieldName, value);
                }
            }

            for (Map.Entry<String, Object> entry : valMapper.entrySet()){
                String fieldName = entry.getKey();
                String methodName = "set" + StringUtils.capitalize(fieldName);
                Object val = entry.getValue();

                setObjectValue(targetObj, methodName, val);
            }

        } catch (InstantiationException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            log.error(e.getMessage(), e);
        }

        return (T) targetObj;
    }

    private static void setObjectValue(Object targetObj, String methodName, Object val) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if (targetObj == null || StringUtils.isEmpty(methodName) || val == null){
            return;
        }

        Class<?> targetClz = targetObj.getClass();
        Class<?> targetArgType = val.getClass();
        Method[] methods = targetClz.getMethods();
        for (Method method : methods){
            if (methodName.equals(method.getName())){
               Method m = getMethod(targetClz, methodName, targetArgType);
               m.invoke(targetObj, val);
            }
        }
    }

    private static Method getMethod(Class<?> targetClz, String methodName, Class<?> targetArgType) throws NoSuchMethodException {
        Class<?>[] argsType = new Class[2];
        if (Integer.class == targetArgType){
            argsType[0] = Integer.class;
            argsType[1] = int.class;
        }else if (Boolean.class == targetArgType){
            argsType[0] = Boolean.class;
            argsType[1] = boolean.class;
        }else if (Double.class == targetArgType){
            argsType[0] = Double.class;
            argsType[1] = double.class;
        }else if (Float.class == targetArgType){
            argsType[0] = Float.class;
            argsType[1] = float.class;
        }else if (Byte.class == targetArgType){
            argsType[0] = Byte.class;
            argsType[1] = byte.class;
        }else if (Long.class == targetArgType){
            argsType[0] = Long.class;
            argsType[1] = long.class;
        }else if (Short.class == targetArgType){
            argsType[0] = Short.class;
            argsType[1] = short.class;
        }else {
            return targetClz.getMethod(methodName, targetArgType);
        }

        return catchMethod(targetClz, methodName, argsType);
    }

    private static Method catchMethod(Class<?> targetClz, String methodName, Class<?>[] argsType){
        Class<?> clz1 = argsType[0];
        Class<?> clz2 = argsType[1];

        try {
            return targetClz.getMethod(methodName, clz1);
        } catch (NoSuchMethodException e) {
            try {
                return targetClz.getMethod(methodName, clz2);
            } catch (NoSuchMethodException noSuchMethodException) {
               log.info(noSuchMethodException.getMessage());
            }
        }

        return null;
    }


    private static Object getObjectValue(Object instance, String methodName) throws InvocationTargetException, IllegalAccessException {
        if (instance == null || StringUtils.isEmpty(methodName)){
            return null;
        }

        Class<?> clz = instance.getClass();
        Method[] methods = clz.getDeclaredMethods();
        for (Method method : methods){
            if (methodName.equals(method.getName())){
                return method.invoke(instance, null);
            }
        }

        return null;
    }
}
