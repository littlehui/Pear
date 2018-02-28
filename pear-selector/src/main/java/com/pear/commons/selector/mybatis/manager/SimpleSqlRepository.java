package com.pear.commons.selector.mybatis.manager;

import com.pear.commons.selector.exception.SqlNotFindException;
import com.pear.commons.selector.mybatis.manager.seletetor.AutoCode;
import com.pear.commons.selector.mybatis.manager.seletetor.UUID;
import com.pear.commons.selector.mybatis.repository.BaseRepository;
import com.pear.commons.selector.mybatis.util.ClassUtil;
import com.pear.commons.selector.mybatis.util.ObjectUtil;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by littlehui on 2016/5/23.
 */
public class SimpleSqlRepository<T> extends BaseRepository<T> implements SqlRepository, SqlRepositoryIniter {

    private static final String DEFAULT_UPDATE_TIME_COLUMN = "updateTime";

    private static final String DEFAULT_CREATE_TIME_COLUMN = "createTime";

    public static Map<String, String> sqlRepository = new ConcurrentHashMap();

    protected String zone = "";

    public String getSqlByName(String name) {
        String sql = sqlRepository.get(name + "_" + this.zone);
        if (sql == null) {
            throw new SqlNotFindException("无法找到配置的sql");
        }
        return sql;
    }

    public void addSqlByName(String sqlName, String sqlPrepare) {
        sqlRepository.put(sqlName + "_" + this.zone, sqlPrepare);
    }

    public void initSqlRepository() {
        this.zone = t.getName();
        //在子类里用于添加sql
    }

    /**
     * auto insert updateTime and createTime
     *
     * @param t
     * @return
     */
    public T insert(T t) {
        List<Field> fields = ObjectUtil.getAnnotaionFields(t.getClass(), AutoCode.class);
        if (fields != null && fields.size() > 0) {
            for (Field field : fields) {
                String randCode = this.randCode();
                ObjectUtil.setProperty(t, field.getName(), randCode);
            }
        }
        if (ClassUtil.hasProperty(t.getClass(), SimpleSqlRepository.DEFAULT_UPDATE_TIME_COLUMN)) {
            ObjectUtil.setProperty(t, SimpleSqlRepository.DEFAULT_UPDATE_TIME_COLUMN, System.currentTimeMillis());
        }
        if (ClassUtil.hasProperty(t.getClass(), SimpleSqlRepository.DEFAULT_CREATE_TIME_COLUMN)) {
            ObjectUtil.setProperty(t, SimpleSqlRepository.DEFAULT_CREATE_TIME_COLUMN, System.currentTimeMillis());
        }
        return super.insert(t);
    }

    /**
     * auto update updateTime
     *
     * @param t
     */
    public void update(T t) {
        if (ClassUtil.hasProperty(this.t, SimpleSqlRepository.DEFAULT_UPDATE_TIME_COLUMN)) {
            ObjectUtil.setProperty(t, SimpleSqlRepository.DEFAULT_UPDATE_TIME_COLUMN, System.currentTimeMillis());
        }
        super.update(t);
    }

    private String randCode() {
        return UUID.generate();
    }
}
