package com.pear.commons.selector.mybatis.manager;

/**
 * Created by littlehui on 2016/5/20.
 */
public interface SqlRepository {

     String getSqlByName(String name);

     void addSqlByName(String sqlName, String sqlPrepare);
}
