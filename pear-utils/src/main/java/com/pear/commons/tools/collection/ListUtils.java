package com.pear.commons.tools.collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.pear.commons.tools.object.ObjectUtils.getPropValueByName;

/**
 * User: littlehui Date: 14-10-29 Time: 上午10:49
 */
public class ListUtils {

    private final static Logger logger = LoggerFactory.getLogger(ListUtils.class);

    /**
     * 获取list里面单个对象的单个属性重新组装成一个list
     *
     * @param list
     * @param columnName
     * @param columnClass
     * @param <T>
     * @return
     */
    public static <T> List<T> getListItemsSingleColumnList(List list, String columnName, Class<T> columnClass) {
        List<T> returnList = new ArrayList<T>();
        if (!ListUtils.isEmpty(list)) {
            for (Object object : list) {
                Object columnObject = getPropValueByName(object, columnName);
                if (columnObject != null) {
                    returnList.add((T) columnObject);
                }
            }
        }
        return returnList;
    }

    public static boolean isEmpty(Collection c) {
        if (c == null || c.size() < 1) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Collection c) {
        return !isEmpty(c);
    }

    /**
     * 拼装list成String.
     *
     * @param list
     * @param separator
     *            分隔符
     * @return
     * @author QiSF
     * @date 2014年12月19日
     */
    public static String list2String(List list, String separator) {
        StringBuffer returnStr = new StringBuffer("");
        if (isNotEmpty(list)) {
            int i = 0;
            for (Object o : list) {
                returnStr.append(o);
                i++;
                if (i < list.size()) {
                    returnStr.append(separator);
                }
            }
        }
        return returnStr.toString();
    }

    public static List<Object> toObjectList(String[] classifyCodes) {
        List<Object> objects = new ArrayList();
        for (String code : classifyCodes) {
            objects.add((Object)code);
        }
        return objects;
    }

    public static List<Object> toObjectList(List<String> classifyCodes) {
        List<Object> objects = new ArrayList();
        for (String code : classifyCodes) {
            objects.add(code);
        }
        return objects;
    }
}
