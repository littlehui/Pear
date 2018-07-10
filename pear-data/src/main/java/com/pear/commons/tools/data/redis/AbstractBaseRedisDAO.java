package com.pear.commons.tools.data.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public abstract class AbstractBaseRedisDAO<T> {

    @Autowired
    protected RedisTemplate redisTemplate;

    /**
     * 设置redisTemplate
     *
     * @param redisTemplate the redisTemplate to set
     */
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取 RedisSerializer
     * <br>------------------------------<br>
     */
    protected RedisSerializer getRedisSerializer() {
        return redisTemplate.getDefaultSerializer();
    }


    ValueOperations<String, T> valueOper;
    ValueOperations<String, Collection<T>> collectionOper;
    ListOperations<String, T> listOper;
    SetOperations<String, T> setOperater;

    public AbstractBaseRedisDAO() {
    }

    @PostConstruct
    public void init() {
        valueOper = redisTemplate.opsForValue();
        collectionOper = redisTemplate.opsForValue();
        listOper = redisTemplate.opsForList();
        setOperater = redisTemplate.opsForSet();
    }


    protected String zone;

    public void save(String key, T data) {
        valueOper.set(zone + key, data);
    }

    public void save(String key, T data, long timeStamp) {
        valueOper.set(zone + key, data, timeStamp, TimeUnit.MILLISECONDS);
    }

    public void listLeftPush(String key, T data) {
        listOper.leftPush(zone + key, data);
    }

    public void saveCollection(String key, Collection<T> datas) {
        collectionOper.set(zone + "collection:" + key, datas);
    }

    public void saveCollection(String key, Collection<T> datas, long timeStamp) {
        collectionOper.set(zone + "collection:" + key, datas, timeStamp, TimeUnit.MILLISECONDS);
    }

    public Collection<T> getCollection(String key) {
        return collectionOper.get(zone + "collection:" + key);
    }

    public void listRightPush(String key, T data) {
        listOper.rightPush(zone + "list:" + key, data);
    }

    public Long getListSize(String key) {
        return listOper.size(zone + "list:" + key);
    }

    public T get(String key) {
        return valueOper.get(zone + key);
    }

    public T listLeftPop(String key) {
        return listOper.leftPop(zone + "list:" + key);
    }

    public T rightPop(String key) {
        return listOper.rightPop(zone + "list:" + key);
    }

    public void delete(String key) {
        redisTemplate.delete(zone + key);
    }

    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public void deleteByPattern(String pattern) {
        Set<T> keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }

    public void delete(String... keys) {
        List<String> newKeys = new ArrayList<String>();
        for (String key : keys) {
            newKeys.add(zone + key);
        }
        redisTemplate.delete(newKeys);
    }

    public void deleteCollection(String... keys) {
        List<String> newKeys = new ArrayList<String>();
        for (String key : keys) {
            newKeys.add(zone + "collection:" + key);
        }
        redisTemplate.delete(newKeys);
    }

    public Long incr(String key) {
        return redisTemplate.boundValueOps(zone + key).increment(1);
    }

    public String getZone() {
        return zone;
    }

    public List<T> lRangeAll(String key) {
        return listOper.range(zone + key, 0, -1);
    }

    public List<T> lRangeSize(String key, Integer size) {
        return listOper.range(zone + key, 0, size - 1);
    }

    public T leftPop(String key) {
        return listOper.leftPop(zone + key);
    }

    public void leftPush(String key, T data) {
        this.listOper.leftPush(zone + key, data);
    }

    public void     setAdd(String key, T data) {
        setOperater.add(zone + key, data);
    }

    protected Long setMemberSize(String key) {
        return setOperater.size(zone + key);
    }


    public Set<T> setGet(String key) {
        return setOperater.members(zone + key);
    }

    public Long removeSet(String key, T data) {
        Long result = setOperater.remove(zone + key, data);
        return result;
    }


}
