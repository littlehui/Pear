package com.pear.commons.selector.mybatis.util;

import com.pear.commons.selector.mybatis.annotation.Id;
import com.pear.commons.selector.mybatis.annotation.Table;

import java.lang.reflect.Field;

/**
 * User: littlehui
 * Date: 13-11-11
 * Time: ����5:12
 */
public class ColumnUtils {

    public final static <M> String getTableName(Class<M> clazz) {
        try {
            Table table = clazz.getAnnotation(Table.class);
            return table.value();
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("table name not set!");
    }

    /**
     * ��ȡ�����idע�����������
     * @param clazz
     * @param <M>
     * @return
     */
    public final static <M> String getIdFieldName(Class<M> clazz ) {
        for(Field field : clazz.getDeclaredFields()) {
            Id idField = field.getAnnotation(Id.class);
            if (idField == null) {
                continue;
            }
            return field.getName();
        }
        throw new RuntimeException("@id field not find ");
    }

}
