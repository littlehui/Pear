package com.pear.commons.selector.test;

import com.pear.commons.selector.mybatis.util.ObjectUtil;

/**
 * Created by littlehui on 2017/6/18.
 */
public class ObjectTests {
    public static void main(String[] args) {
        SourceObject sourceObject = new SourceObject();
        sourceObject.setColumn1("tests1");
        sourceObject.setColumn2("test2");
        sourceObject.setColumn3(12312);
        sourceObject.setColumn4(232323L);
        DestObject destObject = new DestObject();
        ObjectUtil.copyProperties(sourceObject, destObject);
        System.out.println(destObject);
        DestObject destObject1 = ObjectUtil.convertObj(sourceObject, DestObject.class);
        System.out.println(destObject1.getColumn1());
    }
}
