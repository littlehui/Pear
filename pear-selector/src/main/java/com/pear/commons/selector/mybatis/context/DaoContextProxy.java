package com.pear.commons.selector.mybatis.context;

import com.pear.commons.selector.mybatis.dao.BaseDAO;
import org.springframework.context.ApplicationContext;

/**
 * Created by littlehui on 2017/7/17.
 */
public class DaoContextProxy implements DaoContext {

    public <T> BaseDAO<T> getDAO(String daoName) {

        return null;
    }

    public <T> BaseDAO<T> getDAO(Class<T> beanClass) {
        return null;
    }
}
