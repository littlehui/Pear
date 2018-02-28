package com.pear.commons.tools.quartz.manager;

import com.pear.commons.tools.quartz.bean.TriggerVo;
import org.quartz.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * User: littlehui
 * Date: 14-5-12
 * Time: 下午4:37
 */
public class QuartzManager {

    Scheduler quartzScheduler;

    public Scheduler getQuartzScheduler() {
        return quartzScheduler;
    }

    public void setQuartzScheduler(Scheduler quartzScheduler) {
        this.quartzScheduler = quartzScheduler;
    }

    public JobDetail addJob(String jobName, Class<? extends Job> jobClass, String group, Map<String, Object> dataMap) {
        try {
            JobDetail jobDetail = new JobDetail();
            jobDetail.setJobClass(jobClass);
            jobDetail.setName(jobName);
            jobDetail.setGroup(group);
            jobDetail.setDurability(true);
            //触发器相关内容
            JobDataMap jobDataMap = new JobDataMap();
            for (String key : dataMap.keySet()) {
                jobDataMap.put(key, dataMap.get(key) + "");
            }
            jobDetail.setJobDataMap(jobDataMap);
            quartzScheduler.addJob(jobDetail, true);
            return jobDetail;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 添加一个关联JobDetail的定时触发器.
     *
     * @param triggerName   触发器名
     * @param jobDetail     关联任务
     * @param dataMap       触发器内容
     * @param keepTimeMills 持续多久
     *                      -1永久
     * @throws SchedulerException 任务调度异常
     */
   /* public void addTrigger(String triggerName, JobDetail jobDetail,
                           Map<String, Object> dataMap, Date date, Long keepTimeMills) throws ParseException {
        //触发器相关内容
        String cronExpression = CronExpressionUtil.DateToTimingExpression(date);
        try {
            JobDataMap jobDataMap = new JobDataMap();
            for (String key : dataMap.keySet()) {
                jobDataMap.put(key, dataMap.get(key));
            }
            CronTriggerBean trigger = new CronTriggerBean();
            trigger.setCronExpression(cronExpression);
            trigger.setName(triggerName);
            trigger.setJobDataAsMap(dataMap);
            Date startDate = new Date();
            Long currentMills = System.currentTimeMillis();
            trigger.setStartTime(startDate);
            trigger.setJobGroup(jobDetail.getGroup());
            if (keepTimeMills > 0) {
                trigger.setEndTime(new Date(currentMills + keepTimeMills + 60 * 1000));
            }
            trigger.setJobDetail(jobDetail);
            trigger.setJobName(jobDetail.getName());
            trigger.setGroup(jobDetail.getGroup());
            //添加触发器.
            quartzScheduler.scheduleJob(trigger);
        } catch (SchedulerException e) {
            try {
                quartzScheduler.deleteJob(jobDetail.getName(), jobDetail.getGroup());
            } catch (SchedulerException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

    }*/
    public List<TriggerVo> getTriggersByGroup(String group) {
        List<TriggerVo> triggerVos = new ArrayList<TriggerVo>();
        try {
            List<JobExecutionContext> executionContexts = quartzScheduler.getCurrentlyExecutingJobs();
            for (JobExecutionContext jobExecutionContext : executionContexts) {
                JobDetail job = jobExecutionContext.getJobDetail();
                if (job != null) {
                    TriggerVo triggerVo = new TriggerVo();
                    triggerVo.setJobName(job.getKey().getName());
                    triggerVo.setJobGroup(job.getKey().getGroup());
                    Trigger[] trigger = quartzScheduler.getTriggersOfJob(job.getName(), job.getGroup());
                    List<Trigger> triggers = Arrays.asList(trigger);
                    if (triggers != null && triggers.size() > 0) {
                        triggerVo.setNextFireTime(triggers.get(0).getNextFireTime().getTime());
                        triggerVo.setTriggerName(triggers.get(0).getKey().getName());
                        triggerVo.setStartTime(triggers.get(0).getStartTime().getTime());
                        if (triggers.get(0).getPreviousFireTime() != null) {
                            triggerVo.setPrevFireTime(triggers.get(0).getPreviousFireTime().getTime());
                        }
                        triggerVos.add(triggerVo);
                    }
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return triggerVos;
    }

    /**
     * 根据触发器名和触发器组名获取触发器
     *
     * @param triggerName 触发器名
     * @return Trigger 触发器
     * @throws SchedulerException
     */
    public Trigger getTrigger(String triggerName, String groupName) throws SchedulerException {
        return quartzScheduler.getTrigger(triggerName, groupName);
    }

    public TriggerVo getTriggerVo(String name, String group) {
        try {
            Trigger trigger = quartzScheduler.getTrigger(name, group);
            TriggerVo triggerVo = new TriggerVo();
            if (trigger != null) {
                triggerVo.setJobGroup(trigger.getJobGroup());
                triggerVo.setJobName(trigger.getJobName());

                if (trigger.getNextFireTime() != null) {
                    triggerVo.setNextFireTime(trigger.getNextFireTime().getTime());
                }
                triggerVo.setTriggerName(trigger.getName());
                if (trigger.getStartTime() != null) {
                    triggerVo.setStartTime(trigger.getStartTime().getTime());
                }
                if (trigger.getPreviousFireTime() != null) {
                    triggerVo.setPrevFireTime(trigger.getPreviousFireTime().getTime());
                }
            }
            return triggerVo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JobDetail getJob(String jobName, String groupName) throws SchedulerException {
        return quartzScheduler.getJobDetail(jobName, groupName);
    }

    /**
     * 删除job。job相关的trigger也一并删了
     *
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException
     */
    public void deleteJob(String jobName, String jobGroup) throws SchedulerException {
        quartzScheduler.deleteJob(jobName, jobGroup);
    }

    public void reschdulerJob(String triggerName, String group, Trigger trigger) {
        try {
            quartzScheduler.rescheduleJob(triggerName, group, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void resumeTrigger(String triggerName, String triggerGroup) {
        try {
            quartzScheduler.resumeTrigger(triggerName, triggerGroup);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public List<TriggerVo> getAllTriggers() {
        List<TriggerVo> triggerVos = new ArrayList<TriggerVo>();
        List<TriggerVo> defaultJobs = this.getTriggers("DEFAULT");
        if (defaultJobs != null) {
            triggerVos.addAll(defaultJobs);
        }
        return triggerVos;
    }

    public List<TriggerVo> getTriggers(String group) {
        List<TriggerVo> triggerVos = new ArrayList<TriggerVo>();
        String[] gropJobs = new String[0];
        try {
            gropJobs = quartzScheduler.getJobNames(group);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        if (gropJobs != null && gropJobs.length > 0) {
            for (int i = 0; i < gropJobs.length; i++) {
                try {
                    JobDetail job = quartzScheduler.getJobDetail(gropJobs[i], group);
                    if (job != null) {
                        TriggerVo triggerVo = new TriggerVo();
                        triggerVo.setJobGroup(job.getGroup());
                        triggerVo.setJobName(job.getName());
                        Trigger[] trigger = quartzScheduler.getTriggersOfJob(job.getName(), job.getGroup());
                        if (trigger != null && trigger.length > 0) {
                            if (trigger[0].getNextFireTime() != null) {
                                triggerVo.setNextFireTime(trigger[0].getNextFireTime().getTime());
                            }
                            triggerVo.setTriggerName(trigger[0].getName());
                            if (trigger[0].getStartTime() != null) {
                                triggerVo.setStartTime(trigger[0].getStartTime().getTime());
                            }
                            if (trigger[0].getPreviousFireTime() != null) {
                                triggerVo.setPrevFireTime(trigger[0].getPreviousFireTime().getTime());
                            }
                            triggerVos.add(triggerVo);
                        }
                    }
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }

            }
        }
        return triggerVos;
    }

    public void resumeAll(String group) {
        List<TriggerVo> vos = this.getTriggers(group);
        if (vos != null && vos.size() > 0) {
            for (TriggerVo vo : vos) {
                resumeTrigger(vo.getTriggerName(), vo.getJobGroup());
            }
        }
    }
}
