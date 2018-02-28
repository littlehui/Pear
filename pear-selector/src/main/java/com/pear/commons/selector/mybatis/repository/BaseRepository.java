package com.pear.commons.selector.mybatis.repository;

import com.pear.commons.selector.exception.CrudException;
import com.pear.commons.selector.mybatis.bean.Paged;
import com.pear.commons.selector.mybatis.bean.Query;
import com.pear.commons.selector.mybatis.bean.SqlQuery;
import com.pear.commons.selector.mybatis.dao.BaseDAO;
import com.pear.commons.selector.mybatis.util.ClassUtil;
import com.pear.commons.selector.mybatis.util.ColumnUtils;
import com.pear.commons.selector.mybatis.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Locale.ENGLISH;

/**
 * User: littlehui
 * Date: 13-11-19
 * Time: 上午9:13
 */
public class BaseRepository<T> implements ApplicationContextAware {

    private final static Logger logger = LoggerFactory.getLogger(BaseRepository.class);

    protected Class<T> t;

    private ApplicationContext applicationContext;

    public BaseRepository() {
        if (this.t == null) {
            Class<T> type = ClassUtil.getActualType(this.getClass());
            if (type == null) {
                throw new RuntimeException("继承类没有加泛型!");
            }
            this.t = type;
        }
    }

    public BaseRepository(Class t) {
        this.t = t;
    }

    public int count(Query query) {
        return getDAO().count(query);
    }
    /**
     * 插入对象
     * @param obj
     * @return
     */
    public T insert(T obj) {
        try {
            getDAO().insert(obj);
            return obj;
        } catch (Exception e){
            e.printStackTrace();
            throw new CrudException(e);
        }
    }

    public void insertBatch(List<T> tList) {
        for (T t : tList) {
            this.insert(t);
        }
    }

    /**
     * 获取单个
     * @param id
     * @return
     */
    public T get(Integer id){
        if (id == null) {
            return null;
        }
        try {
            Query query = Query.build(t);
            query.addEq(ColumnUtils.getIdFieldName(t),id);
            List<T> objects = findByQuery(query);
            if (objects.size() > 0) {
                return objects.get(0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public T get(Query query){
        if (query == null) {
            return null;
        }
        query.setPaged(1,1);
        List<Map<String,Object>> list = getDAO().findByQuery(query);
        List<T> objects = ObjectUtil.toBeanList(t, list);
        if (objects.size() > 0) {
            return objects.get(0);
        }
        return null;
    }

    public void delete(int id) {
        try {
            Query query = Query.build(t);
            query.addEq(ColumnUtils.getIdFieldName(t),id);
            getDAO().deleteByQuery(query);
        }catch (Exception e){
            e.printStackTrace();
            throw new CrudException(e);
        }
    }

    public void update(T obj) {
        try {
            getDAO().update(obj);
        }catch (Exception e){
            e.printStackTrace();
            throw new CrudException(e);
        }
    }

    public void delete(T obj) {
        try {
            getDAO().delete(obj);
        }catch (Exception e){
            e.printStackTrace();
            throw new CrudException(e);
        }
    }

    public void removeByQuery(Query query) {
        try {
            getDAO().deleteByQuery(query);
        }catch (Exception e){
            e.printStackTrace();
            throw new CrudException(e);
        }
    }

    /**
     * 查询所有的
     * @return
     */
    public List<T> findAll(){
        try {
            Query query = Query.build(t);
            List<T> objects = findByQuery(query);
            return objects;
        } catch (Exception e){
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


    public List<T> findByQuery(Query query){
        try {
            List<Map<String,Object>> list = getDAO().findByQuery(query);
            List<T> objects = ObjectUtil.toBeanList(t, list);
            return objects;
        }catch (Exception e){
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * 获取分页数据
     * @param query
     * @return
     */
    public Paged<T> findPagedByQuery(Query query){
        List<T> objects = findByQuery(query);
        int count = getDAO().count(query);
        return new Paged<T>(objects ,count ,query.getPageNo() ,query.getPageSize());
    }

    protected BaseDAO<T> getDAO(){
        String daoName = lowerTop(t.getSimpleName()) +"DAO";
        //此处剥离applicationContext
        //代理
        if (applicationContext.containsBean(daoName)) {
            Object dao =  applicationContext.getBean(daoName);
            if (dao != null ) {
                return (BaseDAO<T>)dao;
            }else {
                logger.error("bean not exist by name:"+ daoName);
            }
        }else {
            logger.error("bean not exist by name:"+ daoName);
        }
        return null;
    }

    /**
     * 使用sql语句查询分页
     *
     * @param sql
     * @param retClass
     * @param pageNo
     * @param pageSize
     * @param params
     * @param <M>
     * @return
     */
    public <M> Paged<M> findPageBySql(String sql, Class<M> retClass, Integer pageNo, Integer pageSize, Object... params) {
        SqlQuery sqlQuery = new SqlQuery(sql, pageNo, pageSize, params);
        List<Map<String, Object>> list = getDAO().findBySqlQuery(sqlQuery);
        List<M> objects = ObjectUtil.toBeanExtList(retClass, list);
        int totalCount = getDAO().countBySqlQuery(sqlQuery);
        return new Paged<M>(objects, totalCount, pageNo, pageSize);
    }

    /**
     * 使用sql语句查询分页
     *
     * @param sql
     * @param retClass
     * @param pageNo
     * @param pageSize
     * @param params
     * @param <M>
     * @return
     */
    public <M> Paged<M> findPageBySqlInDistinctParam(String sql, Class<M> retClass, Integer pageNo, Integer pageSize, String distinctParam, Object... params) {
        SqlQuery sqlQuery = new SqlQuery(sql, pageNo, pageSize, params);
        sqlQuery.setDistinctParam(distinctParam);
        List<Map<String, Object>> list = getDAO().findBySqlQuery(sqlQuery);
        List<M> objects = ObjectUtil.toBeanExtList(retClass, list);
        int totalCount = getDAO().countBySqlQuery(sqlQuery);
        return new Paged<M>(objects, totalCount, pageNo, pageSize);
    }

    /**
     * 使用sql语句查询列表
     *
     * @param sql      完整SQL语句
     * @param retClass 需要返回的类型
     * @param params
     * @param <M>
     * @return
     */
    public <M> List<M> findBySql(String sql, Class<M> retClass, Object... params) {
        SqlQuery sqlQuery = new SqlQuery(sql, params);
        List<Map<String, Object>> list = getDAO().findBySqlQuery(sqlQuery);
        return ObjectUtil.toBeanExtList(retClass, list);
    }

    public List<T> findBySql(String sql, Object... params) {
        SqlQuery sqlQuery = new SqlQuery(sql, params);
        List<Map<String, Object>> list = getDAO().findBySqlQuery(sqlQuery);
        return ObjectUtil.toBeanExtList(t, list);
    }




    /**
     * Returns a String which capitalizes the first letter of the string.
     */
    public static String lowerTop(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        name = name.replace("PO", "");
        return name.substring(0, 1).toLowerCase(ENGLISH) + name.substring(1);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
