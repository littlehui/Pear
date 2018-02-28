package com.pear.commons.selector.mybatis.context;

/**
 * Created by littlehui on 2017/7/18.
 */
public class DaoContextFactory {

    DaoContext daoContext;

    public DaoContext createDaoContext() {
        if (daoContext == null) {
            throw new RuntimeException("daoContext has not injection.");
        }
        return daoContext;
    }
}
