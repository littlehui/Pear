package com.pear.commons.tools.data.redis;


import java.util.ArrayList;
import java.util.List;

/**
 * User: littlehui
 * Date: 2015/12/3
 * Time: 11:07
 * @author littlehui
 */
public class BaseRedisQueue<T> extends BaseRedisDAO<T> {

    public String adderQueue;

    public BaseRedisQueue() {
        this.zone = "default_queue_zone_";
        adderQueue = "default_queue_";
    }

    public void pushAdder(T t) {
        this.leftPush(adderQueue, t);
    }

    public T popAdder() {
        return this.leftPop(adderQueue);
    }

    public List<T> rangeAll() {
        return this.lRangeAll(adderQueue);
    }

    public void clean() {
        this.delete(adderQueue);
    }

    public List<T> rangeBySize(Integer size) {
        return this.lRangeSize(adderQueue, size);
    }

    public List<T> popSize(Integer size) {
        List<T> popOrders = new ArrayList<>();
        for (int i=0; i<size; i++) {
            T s = this.popAdder();
            if (s != null) {
                popOrders.add(s);
            } else {
                break;
            }
        }
        return popOrders;
    }
}
