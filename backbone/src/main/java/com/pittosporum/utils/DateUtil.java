package com.pittosporum.utils;

import com.pittosporum.constant.DateConstant;
import com.pittosporum.exception.BaseRunException;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public final class DateUtil {
    private DateUtil(){}

    public static final Locale DFT_LOCALE = new Locale("en", "cn");

    public static String formatDate(Date date) {
        return formatDateTime(date, DateConstant.DEFAULT_DATE_FORMAT);
    }

    public static String formatDate(Date date, String pattern) {
        return formatDateTime(date, pattern);
    }

    private static String formatDateTime(Date date, String pattern) {
        if (date == null) {
            return "";
        }

        if (pattern == null) {
            // default pattern will be used.
            pattern = DateConstant.DEFAULT_DATE_TIME_FORMAT;
        }

        DateFormat df = new SimpleDateFormat(pattern, DFT_LOCALE);
        return df.format(date);
    }

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
