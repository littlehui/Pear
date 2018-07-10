package com.pear.commons.tools.data.redis;

/**
 * Created by mac on 15/4/22.
 */
public class BaseRedisDAO<T> extends AbstractBaseRedisDAO<T> {

    public BaseRedisDAO() {
        this.zone = "global:";
    }

}
