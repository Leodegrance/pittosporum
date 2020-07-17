package pittosporum.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import pittosporum.exception.BaseRunException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Slf4j
public class JsonUtil {
    private JsonUtil() {
    }

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    public static String parseToJson(Object obj) throws BaseRunException {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new BaseRunException(e);
        }
    }

    public static <T> List<T> parseToList(String jsonStr, Class<T> clz) throws BaseRunException {
        JavaType type = mapper.getTypeFactory().constructParametricType(List.class, clz);
        List<T> tList;
        try {
            tList = mapper.readValue(jsonStr, type);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new BaseRunException(e);
        }
        return tList;
    }

    public static <T> T parseToObject(String json, Class<T> clz) throws BaseRunException {
        ObjectReader or = mapper.readerFor(clz);
        try {
            T obj = or.readValue(json);
            return obj;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new BaseRunException(e);
        }
    }

    public static <T> List<T> transferListContent(List<Map<String, Object>> inList, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        inList.forEach(map ->
                list.add(tranMapToDto(map, cls))
        );

        return list;
    }

    private static <T> T tranMapToDto(Map<String, Object> map, Class<T> cls) throws BaseRunException {
        if (cls == null || map == null || map.isEmpty()) {
            return null;
        }

        try {
            T obj = cls.newInstance();
            for (Map.Entry<String, Object> ent : map.entrySet()) {
                if (ent.getValue() == null) {
                    continue;
                }

                Field field = cls.getDeclaredField(ent.getKey());
                Method method = cls.getMethod("set" + StringUtils.capitalize(ent.getKey()),
                        field.getType());
                if (ent.getValue() instanceof Map && !Map.class.isAssignableFrom(field.getType())) {
                    method.invoke(obj, tranMapToDto((Map) ent.getValue(), field.getType()));
                } else if (Date.class.isAssignableFrom(field.getType())) {
                    try {
                        method.invoke(obj, DateUtil.parseToDate((String) ent.getValue(),
                                "yyyy-MM-dd HH:mm:ss"));
                    }catch (ClassCastException e){
                        method.invoke(obj, new Date((long) ent.getValue()));
                        log.error(e.getMessage());
                    }

                } else if (field.getType().isAssignableFrom(ent.getValue().getClass())
                        || (ent.getValue() instanceof Number && isNumericType(field.getType()))
                        || (ent.getValue() instanceof Boolean && isBooleanType(field.getType()))
                        || (ent.getValue() instanceof Date && Date.class.isAssignableFrom(field.getType()))) {
                    method.invoke(obj, ent.getValue());
                }
            }

            return obj;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException
                | NoSuchFieldException | ParseException e) {
            log.error(e.getMessage(), e);
            throw new BaseRunException(e);
        }
    }

    private static boolean isBooleanType(Class cls) {
        return Boolean.TYPE.equals(cls) || Boolean.class.equals(cls);
    }

    private static boolean isNumericType(Class cls) {
        return Short.TYPE.equals(cls) || Short.class.equals(cls)
                || Integer.TYPE.equals(cls) || Integer.class.equals(cls)
                || Long.TYPE.equals(cls) || Long.class.equals(cls)
                || Float.TYPE.equals(cls) || Float.class.equals(cls)
                || Double.TYPE.equals(cls) || Double.class.equals(cls);
    }
}
