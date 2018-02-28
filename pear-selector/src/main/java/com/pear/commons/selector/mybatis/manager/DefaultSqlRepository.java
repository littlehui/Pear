package com.pear.commons.selector.mybatis.manager;

import com.pear.commons.selector.mybatis.bean.Paged;
import com.pear.commons.selector.mybatis.bean.SqlQuery;
import com.pear.commons.selector.mybatis.util.ClassUtil;
import com.pear.commons.selector.mybatis.util.ObjectUtil;

import java.util.List;

/**
 * Created by littlehui on 2016/5/20.
 */
public class DefaultSqlRepository<T> extends SimpleSqlRepository<T> {

    public DefaultSqlRepository() {
        if (this.t == null) {
            Class type = ClassUtil.getActualType(this.getClass());
            if (type == null) {
                throw new RuntimeException("继承类没有加泛型!");
            }
            this.t = type;
        }
        this.initSqlRepository();
    }

    public List<T> findBySqlName(String sqlName, Object... params) {
        String sql = this.getSqlByName(sqlName);
        return this.findBySql(sql, params);
    }

    public T getOneBySqlName(String sqlName, Object... param) {
        String sql = this.getSqlByName(sqlName);
        List<T> lists = this.findBySql(sql, param);
        if (lists != null && lists.size() > 0) {
            return lists.get(0);
        } else {
            return null;
        }
    }

    public Paged<T> findPageBySqlName(String sqlName, Integer pageNo, Integer pageSize, Object... params) {
        String sql = this.getSqlByName(sqlName);
        SqlQuery sqlQuery = new SqlQuery(sql, pageNo, pageSize, params);
        List list = this.getDAO().findBySqlQuery(sqlQuery);
        int totalCount = this.getDAO().countBySqlQuery(sqlQuery);
        List objects = ObjectUtil.toBeanExtList(this.t, list);
        return new Paged(objects, totalCount, pageNo.intValue(), pageSize.intValue());
    }
}
