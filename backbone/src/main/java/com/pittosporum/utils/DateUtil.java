package com.pittosporum.utils;

import org.springframework.util.StringUtils;
import com.pittosporum.constant.DateConstant;
import com.pittosporum.exception.BaseRunException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public final class DateUtil {
    private DateUtil(){}

    public static Date parseToDate(String val) throws ParseException {
        return parseToDate(val, DateConstant.DEFAULT_DATE_TIME_FORMAT);
    }

    public static Date parseToDate(String val, String pattern) throws ParseException {
        if(StringUtils.isEmpty(val) || StringUtils.isEmpty(pattern)){
            throw new BaseRunException("No has input for String to Date!");
        }

        Date date;
        try {
            DateFormat df = new SimpleDateFormat(pattern);
            date = df.parse(val);
        } catch (ParseException e) {
            throw e;
        }

        return date;
    }
}
