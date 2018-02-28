package com.pear.commons.tools.spring.mvc;

import com.pear.commons.tools.charactor.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间转换
 */
public class DateConverter implements Converter<String, Date> {

    public static final DateFormat DTE_LONG = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");

    public static final DateFormat DTE_SHORT = new SimpleDateFormat("yyyy-MM-dd");
    /*** 短类型日期长度 */
    public static final int SHORT_DATE_LENGTH = 10;

    public Date convert(String source) {
        try {
            if (StringUtils.isEmpty(source)) {
                return null;
            }

            if (StringUtils.isNumeric(source)) {
                return new Date(Long.valueOf(source));
            }

            if (source.length() <= SHORT_DATE_LENGTH) {
                return  (new java.sql.Date(DTE_SHORT.parse(source).getTime()));
            } else {
                return (new java.sql.Timestamp(DTE_LONG.parse(source).getTime()));
            }
        } catch (ParseException ex) {
            RuntimeException iae = new RuntimeException( "Could not parse date: " + ex.getMessage());
            iae.initCause(ex);
            throw iae;
        }
    }
}  