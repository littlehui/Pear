package com.pear.commons.selector.mybatis.dao;



import com.pear.commons.selector.mybatis.bean.MultiQuery;
import com.pear.commons.selector.mybatis.bean.Query;
import com.pear.commons.selector.mybatis.provider.MultiCrudProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * User: littlehui
 * Date: 14-5-7
 * Time: 下午4:26
 */
public interface MultiQueryBaseDAO<T> extends BaseDAO<T> {

    /**
     * 用于支持MultiQuery。不能删掉哦
     * @param multiQuery
     * @return
     */
    @SelectProvider(type =  MultiCrudProvider.class,method = "multiCount")
    public int multiCount(MultiQuery<T> multiQuery);
    /**
     * 构造Query进行查询,返回值可以用ObjectUtil.toBeanList 转成List<T>
     * @param query
     * @return
     */
    @SelectProvider(type =  MultiCrudProvider.class,method = "findByQuery")
    public List<Map<String,Object>> findByQuery(Query<T> query);
}
