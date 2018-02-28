package com.pear.commons.tools.http.url;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/4/8.
 */
public class ObjectMethodUtil {

    public static Object getObjectFiledValue(Object object, final Field field) {
        // 拿到该属性的gettet方法
        /**
         * 这里需要说明一下：他是根据拼凑的字符来找你写的getter方法的
         * boolean值不判断，扩展其他对象，请补充types的内容。
         * 如果出现NoSuchMethod异常 就说明它找不到那个gettet方法 需要做个规范
         */
        Method m;
        try {
            //noinspection RedundantCast
            m = (Method) object.getClass().getMethod(
                    "get" + getMethodName(field.getName()));
            @SuppressWarnings("RedundantCast")
            Object val = (Object) m.invoke(object);
            return val;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取get名称第一个字符变大写。
     * Title: getMethodName
     * Description:
     *
     * @param fildeName
     * @return
     * @throws Exception String
     * @author LittleHui
     * @date 2013-12-9
     */
    public static String getMethodName(String fildeName) throws Exception {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }
}
