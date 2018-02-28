package com.pear.commons.tools.quartz.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by Administrator on 14-2-18.
 */
public class SchedulerManager {

    private final Logger logger = LoggerFactory.getLogger(SchedulerManager.class);

    private final static ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(10);

    private final static Map<String,ScheduledFuture> runningTasks = new ConcurrentHashMap<String, ScheduledFuture>();

    private final static Map<String,SchedulerCallable> allTasks = new ConcurrentHashMap<String, SchedulerCallable>();

    /**
     * 添加任务
     * @param task 具体执行的任务
     * @param period 执行间隔
     */
    public synchronized void addTask(String key , SchedulerCallable task , long delay ,long period ) {
        if (delay < 0) {
            logger.error("delay error:"+delay);
            delay = 0;
        }

        if (period < 0) {
            logger.error("period error:"+period);
            return;
        }

        SchedulerCallable oldTask = allTasks.get(key);
        if (oldTask != null) {
            removeTask(key);
        }


        allTasks.put(key , task);
        ScheduledFuture future = executor.scheduleAtFixedRate(task, delay, period, TimeUnit.MILLISECONDS);
        runningTasks.put(key , future);
        if (logger.isInfoEnabled()) {
            logger.info("add task key:"+key);
        }
    }

    /**
     * 移除任务
     * @param key
     */
    public synchronized void removeTask(String key) {
        try {
            SchedulerCallable task = allTasks.get(key);
            ScheduledFuture future = runningTasks.get(key);

            if (future == null || task == null) {
                logger.error("task with id:"+key+" not exist!");
                return;
            }

            future.cancel(true);
            task.close();
        }finally {
            if (logger.isInfoEnabled()) {
                logger.info("remove task key:"+key);
            }
            allTasks.remove(key);
            runningTasks.remove(key);
        }
    }


    public  int getTaskSize() {
        return allTasks.size();
    }

    public synchronized void destory(){
        for (String key : allTasks.keySet()) {
            removeTask(key);
        }
    }
}
