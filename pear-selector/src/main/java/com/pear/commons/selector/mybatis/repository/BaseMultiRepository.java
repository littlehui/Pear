package com.pear.commons.selector.mybatis.repository;

import com.pear.commons.selector.exception.CrudException;
import com.pear.commons.selector.mybatis.bean.Paged;
import com.pear.commons.selector.mybatis.bean.Query;
import com.pear.commons.selector.mybatis.dao.BaseDAO;
import com.pear.commons.selector.mybatis.util.ClassUtil;
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
 * Created by Administrator on 2016/10/3 0003.
 */
public class BaseMultiRepository<T> implements ApplicationContextAware {

    private final static Logger logger = LoggerFactory.getLogger(BaseRepository.class);

    protected Class<T> t;

    private ApplicationContext applicationContext;

    public BaseMultiRepository() {
        if (this.t == null) {
            Class<T> type = ClassUtil.getActualType(this.getClass());
            if (type == null) {
                throw new RuntimeException("继承类没有加泛型!");
            }
            this.t = type;
        }
    }

    public BaseMultiRepository(Class t) {
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
