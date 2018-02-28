package com.pear.commons.tools.charactor;

import com.pear.commons.tools.object.ObjectUtils;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by littlehui on 2017/5/12.
 */
public class StringUtils {
    /**
     * 是否为空
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        return (s == null || "".equals(s.trim()));
    }

    /**
     * 是否不为空
     *
     * @param s
     * @return
     */
    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    /**
     * 是否包含中文
     *
     * @param s
     * @return
     */
    public static boolean isContainZH(String s) {
        return isEmpty(s) || Pattern.compile("[\\u4e00-\\u9fa5]").matcher(s).find();
    }

    public static String trim(String s) {
        return s != null ? s.trim() : null;
    }

    public static String filterRegexChar(String s) {
        return s;
    }

    public static String replaceBlank(String s) {
        if (s == null) {
            return null;
        }
        return s.replace(" " , "");
    }

    public static String list2StrWithSplit(List<String> list , String split) {
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            sb.append(str).append(split);
        }
        if (sb.length() == 0) {
            return sb.toString();
        }

        return sb.substring(0 , sb.length() - 1);
    }

    public static <M> String list2StrWithSplit(List<M> list , String fieldName , String split) {
        if (list == null || list.size() == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (M obj : list) {
            sb.append(ObjectUtils.getPropertyValue(obj , fieldName)).append(split);
        }

        return sb.substring(0 , sb.length() - 1);
    }

    /**
     * 过滤搜索的特殊字符
     * @param s
     * @return
     */
    public static String escapeQueryChars(String s) {
        if (s == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // These characters are part of the query syntax and must be escaped
            if (c == '\\' || c == '+' || c == '-' || c == '!'  || c == '(' || c == ')' || c == ':'
                    || c == '^' || c == '[' || c == ']' || c == '\"' || c == '{' || c == '}' || c == '~'
                    || c == '*' || c == '?' || c == '|' || c == '&'  || c == ';' || c == '/'
                    || Character.isWhitespace(c)) {
                sb.append('\\');
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public static String toFirstLowerCase(String s){
        return s.substring(0,1).toLowerCase() + s.substring(1,s.length());
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(toFirstLowerCase("UpTime"));
    }
}
