package com.pear.commons.tools.charactor;

/**
 * Created by littlehui on 2017/6/13.
 */
public class CharUtils {

    public static char[] stringToChars(String stringValue, int startIndex, int endIndex) {
        String aStringValue = stringValue.substring(startIndex, endIndex);
        return aStringValue.toCharArray();
    }

    public static char[] cutStartToChars(String stringValue, int index) {
        int start = 0;
        return stringToChars(stringValue, start, index);
    }

    public static char[] cutEndToChars(String stringValue, int index) {
        int end = stringValue.length();
        return stringToChars(stringValue, index, end);
    }
}
