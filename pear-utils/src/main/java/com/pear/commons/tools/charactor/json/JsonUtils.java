package com.pear.commons.tools.charactor.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.log4j.Logger;

public class JsonUtils {

    private static final Logger log = Logger.getLogger(JsonUtils.class);

    /**
     * 将对象转化为Json格式字符串
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        try {
            if (obj == null) {
                return "";
            }
            return JSON.toJSONString(obj);
        } catch (Exception ex) {
            log.error("toJson error：" + obj.getClass().getName(), ex);
            return "{}";
        }
    }

    /**
     * 将Json格式字符串转化为对象
     *
     * @param <T>
     * @param json
     * @param requiredType
     * @return
     */
    public static <T> T toObject(String json, Class<T> requiredType) {
        try {
            return JSON.parseObject(json, requiredType);
        } catch (Exception ex) {
            log.error("toObject error：" + json + " >> " + requiredType.getName(), ex);
            return null;
        }
    }

    /**
     * 复杂转换
     * @param json
     * @param requiredType
     * @param <T>
     * @return
     */
    public static <T> T toObject(String json, TypeReference<T> requiredType) {
        try {
            return JSON.parseObject(json, requiredType);
        } catch (Exception ex) {
            log.error("toObject error：" + json + " >> " + requiredType.getType(), ex);
            return null;
        }
    }
}
