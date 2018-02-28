package com.pear.commons.tools.object;

import com.pear.commons.tools.charactor.StringUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by littlehui on 2017/5/12.
 */
public class ObjectUtils {

    /**
     * 根据属性名称获取属性值.
     *
     * @param object
     * @param propName
     * @return
     */
    public static Object getPropValueByName(Object object, String propName) {
        try {
            String props[] = propName.split("\\.");
            Object o = object;
            for (String prop : props) {
                o = getPropValueByNameSimple(o, prop);
                if (o == null) {
                    break;
                }
            }
            return o;
        } catch (Exception e) {
            // 异常返回空
            return null;
        }
    }

    public static Object getPropValueByNameSimple(Object object, String propName) {
        try {
            // 优先从方法获取，get+属性(属性第一个字母为转换成大写)
            Object result = getPropValueByMethod(object, propName);
            if (result == null) {
                Field field = object.getClass().getDeclaredField(propName);
                if (field == null) {
                    return null;
                }
                // 获取原来的访问控制权限
                boolean accessFlag = field.isAccessible();
                // 修改访问控制权限
                field.setAccessible(true);
                result = field.get(object);
                field.setAccessible(accessFlag);
            }
            return result;
        } catch (Exception e) {
            // 异常返回空
            return getPropValueByMethod(object, propName);
        }
    }

    /**
     * 根据属性名称获取属性值.
     *
     * @param object
     * @param propName
     * @return
     */
    public static Object getPropValueByMethod(Object object, String propName, Object... params) {
        try {
            StringBuffer sb = new StringBuffer(propName);
            sb.setCharAt(0, Character.toUpperCase(propName.charAt(0)));
            Method method = object.getClass().getDeclaredMethod("get" + sb.toString());
            if (method == null) {
                return null;
            }
            // 获取原来的访问控制权限
            boolean accessFlag = method.isAccessible();
            // 修改访问控制权限
            method.setAccessible(true);
            Object result = method.invoke(object);
            method.setAccessible(accessFlag);
            return result;
        } catch (Exception e) {
            // 异常返回空
            return null;
        }
    }

    public static <T> T setProperty(T t, String propertyName, Object property) {
        try {
            PropertyDescriptor propertyDescriptor = getProperty(Introspector.getBeanInfo(t.getClass()), propertyName);
            Method method = propertyDescriptor.getWriteMethod();
            method.invoke(t, property);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static PropertyDescriptor getProperty(BeanInfo beanInfo, String property) {
        PropertyDescriptor[] propertys = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertys) {
            if (propertyDescriptor.getName().equals(property) && !"class".equals(property)) {
                return propertyDescriptor;
            }
        }
        return null;
    }

    public static <T> Object getPropertyValue(T t, String propertyName) {
        try {
            PropertyDescriptor propertyDescriptor = getProperty(Introspector.getBeanInfo(t.getClass()), propertyName);
            Method method = propertyDescriptor.getReadMethod();
            return method.invoke(t);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * One of the following conditions isEmpty = true, else = false :
     * 满足下列一个条件则为空<br>
     * 1. null : 空<br>
     * 2. "" or " " : 空串<br>
     * 3. no item in [] or all item in [] are null : 数组中没有元素, 数组中所有元素为空<br>
     * 4. no item in (Collection, Map, Dictionary) : 集合中没有元素<br>
     *
     * @param value
     * @return
     * @author yangz
     * @date May 6, 2010 4:21:56 PM
     */
    public static boolean isEmpty(Object value) {
        if (value == null) {
            return true;
        }
        if ((value instanceof String)
                && ((((String) value).trim().length() <= 0) || "null".equalsIgnoreCase((String) value))) {
            return true;
        }
        if ((value instanceof Object[]) && (((Object[]) value).length <= 0)) {
            return true;
        }
        if (value instanceof Object[]) { // all item in [] are null :
            // 数组中所有元素为空
            Object[] t = (Object[]) value;
            for (int i = 0; i < t.length; i++) {
                if (t[i] != null) {
                    if (t[i] instanceof String) {
                        if (((String) t[i]).trim().length() > 0 || "null".equalsIgnoreCase((String) t[i])) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
            return true;
        }
        if ((value instanceof Collection) && ((Collection<?>) value).size() <= 0) {
            return true;
        }
        if ((value instanceof Dictionary) && ((Dictionary<?, ?>) value).size() <= 0) {
            return true;
        }
        if ((value instanceof Map) && ((Map<?, ?>) value).size() <= 0) {
            return true;
        }
        return false;
    }

    /**
     * list<String>判空
     *
     * @param list
     * @return
     * @author wangj
     */
    public static boolean isEmpty(List<String> list) {
        if (list == null) {
            return true;
        }
        if (list.size() == 0) {
            return true;
        }
        List<String> newStrs = new ArrayList<String>();
        for (String str : list) {
            if (!StringUtils.isEmpty(str)) {
                newStrs.add(str);
            }
        }
        if (newStrs.size() > 0) {
            return false;
        }
        return true;
    }

    /**
     * 是否是集合
     *
     * @param obj
     * @return
     * @author yangz
     * @date 2012-9-26 下午03:50:55
     */
    public static boolean isCollection(Object obj) {
        if (obj instanceof Collection<?>) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 对象是否是值类型
     *
     * @param obj
     * @return
     * @author yangz
     * @date 2012-9-26 下午03:01:44
     */
    public static boolean isValueType(Object obj) {
        if (obj instanceof String || obj instanceof Number || obj instanceof Boolean || obj instanceof Character || obj instanceof Date) {
            return true;
        } else {
            return false;
        }
    }
}
