package com.pear.commons.tools.quartz.bean;


import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * User: littlehui
 * Date: 14-5-20
 * Time: 上午9:57
 */
public class TriggerVo implements Serializable {

    private String triggerName;
    private String jobName;
    private String jobGroup;
    private Long nextFireTime;
    private Long prevFireTime = 0L;
    private Long startTime;


    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public Long getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(Long nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public Long getPrevFireTime() {
        return prevFireTime;
    }

    public void setPrevFireTime(Long prevFireTime) {
        this.prevFireTime = prevFireTime;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public String getNextFireDate() {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
        return dateformat.format(new Date(nextFireTime));
    }

    public String getPrevFireDate() {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
        return dateformat.format(new Date(prevFireTime));
    }

    public String getStartDate() {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
        return dateformat.format(new Date(startTime));
    }

}
