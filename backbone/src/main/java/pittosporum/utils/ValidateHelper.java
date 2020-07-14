package pittosporum.utils;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import net.sf.oval.context.FieldContext;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import pittosporum.core.ValidateResult;
import pittosporum.dto.SQLStoreDto;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public final class ValidateHelper {
    private final static String ERR_CODE_MAX_LENGTH = "net.sf.oval.constraint.MaxLength";
    private final static String ERR_CODE_MIN_LENGTH = "net.sf.oval.constraint.MinLength";
    private final static String ERR_CODE_MAX_VALUE = "net.sf.oval.constraint.Max";
    private final static String ERR_CODE_MIN_VALUE = "net.sf.oval.constraint.Min";



    private final static String MAX_LENGTH_MAP_KEY = "max";
    private final static String MIN_LENGTH_MAP_KEY = "min";

    private ValidateHelper(){}

    public static <T> ValidateResult validate(T object, String profile){
        if (object == null){
            return null;
        }

        ValidateResult validateResult = new ValidateResult();
        HashMap<String, String> err = new HashMap<>();

        Validator validator = new Validator();

        List<ConstraintViolation> message;
        if (StringUtils.isEmpty(profile)){
            message = validator.validate(object);
        }else {
            message = validator.validate(object, profile);
        }

        validateResult.setHasError(message.isEmpty() ? false : true);
        for (ConstraintViolation vi : message){
            FieldContext fieldContext = (FieldContext) vi.getCheckDeclaringContext();

            Assert.notNull(fieldContext, "ValidateResult fieldContext is null");

            Field field = fieldContext.getField();

            Assert.notNull(fieldContext, "ValidateResult field is null");

            String fieldName = field.getName();

            String msg = "";
            String errorCode = vi.getErrorCode();
            Map<String, ? extends Serializable> messageVariables = vi.getMessageVariables();
            if (messageVariables != null && !messageVariables.isEmpty()){
                String max = (String) messageVariables.get(MAX_LENGTH_MAP_KEY);
                String min = (String) messageVariables.get(MIN_LENGTH_MAP_KEY);
                String val = (StringUtils.isEmpty(max) ? min : max);

                //example: "{msg/number}"
                if (ERR_CODE_MAX_LENGTH.equals(errorCode) || ERR_CODE_MIN_LENGTH.equals(errorCode)){
                    msg = formatValuesMessage(vi.getMessage(),"&amp", val);
                }else if (ERR_CODE_MAX_LENGTH.equals(errorCode) || ERR_CODE_MIN_VALUE.equals(errorCode)){
                    int signIdx = val.lastIndexOf(".");
                    msg = formatValuesMessage(vi.getMessage(), "&amp", val.substring(0, signIdx));
                }
            }else {
                msg = vi.getMessage();
            }

            err.put(fieldName, msg);
        }


        validateResult.setErrorMap(err);
        return validateResult;
    }


    public static void main(String[] args) {
        SQLStoreDto sqlStoreDto = new SQLStoreDto();
        sqlStoreDto.setCreateBy("aaaaaaaaa");
        sqlStoreDto.setProfileId(0);
        ValidateHelper.validate(sqlStoreDto, null);
    }

    private static String formatValuesMessage(String message, String replace, String val){
        if (StringUtils.isEmpty(message)){
            return message;
        }else {
            if (message.contains("{") && message.contains("}")){
                return message.replace(replace, val).replace("{", "").replace("}", "");
            }else {
                return message + "/" + val;
            }
        }
    }
}
