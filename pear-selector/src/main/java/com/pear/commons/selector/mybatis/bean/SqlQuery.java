package com.pear.commons.selector.mybatis.bean;



import com.pear.commons.selector.mybatis.util.ObjectUtil;

import java.util.List;

/**
 * Created by wangj on 2014/11/11.
 */
public class SqlQuery {

    public static final String PARAMS_FIELD_NAME = "params";

    private String sql;

    private Integer pageNo;

    private Integer pageSize;

    private List<Object> params;

    private String distinctParam;

    public SqlQuery(String sql, Object[] params) {
        this.sql = sql;
        this.params = ObjectUtil.arrayToList(params);
    }

    public SqlQuery(String sql, Integer pageNo, Integer pageSize, Object[] params) {
        this.sql = sql;
        this.params = ObjectUtil.arrayToListExcludeEmpty(params);
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public String getDistinctParam() {
        return distinctParam;
    }

    public void setDistinctParam(String distinctParam) {
        this.distinctParam = distinctParam;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public int getOffset() {
        if (pageNo == null || pageSize == null) {
            return -1;
        }

        return (pageNo - 1) * pageSize;
    }
}
