package com.pear.commons.selector.mybatis.context;

import com.pear.commons.selector.mybatis.dao.BaseDAO;

/**
 * Created by littlehui on 2017/7/17.
 */
public interface DaoContext {

    public <T> BaseDAO<T> getDAO(String daoName);

    public <T> BaseDAO<T> getDAO(Class<T> beanClass);

}
