package com.pear.commons.tools.quartz.scheduler;


import java.util.Calendar;
import java.util.Date;


public class CronExpressionUtil {
    
    /**
     * 将时间转化成定时CronExpression cron时间表达式
     *
     * @param date
     * @return (秒 分 时 月内日期 月 周内日期 年)
     */
    public static String DateToTimingExpression(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        StringBuilder cronExpression = new StringBuilder();
        cronExpression.append(calendar.get(Calendar.SECOND)).append(" ");
        cronExpression.append(calendar.get(Calendar.MINUTE)).append(" ");
        cronExpression.append(calendar.get(Calendar.HOUR_OF_DAY)).append(" ");
        cronExpression.append(calendar.get(Calendar.DAY_OF_MONTH)).append(" ");
        cronExpression.append(calendar.get(Calendar.MONTH) + 1).append(" ");
        cronExpression.append("? ");//一个礼拜的第几天 可以忽略
        cronExpression.append(calendar.get(Calendar.YEAR));
        return cronExpression.toString();
    }

    /**
     * 将时间转化成定时CronExpression cron时间表达式
     *
     * @param date 开始时间
     * @param hour 更新周期 单位：小时
     * @return (秒 分 时 月内日期 月 周内日期 年)
     */

    public static String DateToCycleExpression(Date date, int hour, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        StringBuilder cronExpression = new StringBuilder();
        cronExpression.append(calendar.get(Calendar.SECOND)).append(" ");
        cronExpression.append(calendar.get(Calendar.MINUTE)).append(" ");
        cronExpression.append(calendar.get(Calendar.HOUR_OF_DAY)).append(" ");
        cronExpression.append(calendar.get(Calendar.DAY_OF_MONTH)).append(" ");
        cronExpression.append(calendar.get(Calendar.MONTH) + 1).append(" ");
        cronExpression.append("? ");//一个礼拜的第几天 可以忽略
        cronExpression.append(calendar.get(Calendar.YEAR));
        return cronExpression.toString();
    }


    public static String HourToCronExpression(String hour) {
        StringBuilder cronExpression = new StringBuilder();
        cronExpression.append("0").append(" ");
        cronExpression.append("0").append(" ");
        cronExpression.append("0/" + hour).append(" ");
        cronExpression.append("*").append(" ");
        cronExpression.append("*").append(" ");
        cronExpression.append("? ");//一个礼拜的第几天 可以忽略

        return cronExpression.toString();
    }

    /**
     * 生成Cron表达式.
     *
     * @param day       时间：1-7表示周一到周天，0表示每天.
     * @param startTime
     * @param internal
     * @return
     */

    public static String AnalyzerTimeToCronExpression(   Integer day,    String startTime, Integer internal) {
        String[] time = startTime.split(":");
        StringBuilder cronExpression = new StringBuilder();
        //秒.
        cronExpression.append("0").append(" ");
        //分.
        cronExpression.append(time[1]).append(" ");
        //时.
        cronExpression.append(time[0]);
        if (internal != null) {
            cronExpression.append("/" + internal);
        }
        cronExpression.append(" ");
        cronExpression.append("?").append(" ");
        cronExpression.append("*").append(" ");
        cronExpression.append(makeDayCron(day)).append(" ");
        return cronExpression.toString();

    }

    /**
     * 构造第几天的内容.
     *
     * @param day 第几天.
     * @return
     */
    private static String makeDayCron(   Integer day) {
        String retValue;
        switch (day) {
            case 1:
                retValue = "MON";
                break;
            case 2:
                retValue = "TUE";
                break;
            case 3:
                retValue = "WED";
                break;
            case 4:
                retValue = "THU";
                break;
            case 5:
                retValue = "FRI";
                break;
            case 6:
                retValue = "SAT";
                break;
            case 7:
                retValue = "SUN";
                break;
            default:
                retValue = "*";
                break;
        }
        return retValue;
    }

    /**
     * 哪一时间点，倒计时单位秒
     * @param keepTimeMills
     * @param dataMills
     * @return
     */
    public static String countDown(Long dataMills, Long keepTimeMills) {
        StringBuffer stringBuffer = new StringBuffer();
        Long startMills = dataMills + keepTimeMills;
        Date date = new Date(startMills);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        StringBuilder cronExpression = new StringBuilder();
        cronExpression.append(calendar.get(Calendar.SECOND)).append(" ");
        cronExpression.append(calendar.get(Calendar.MINUTE)).append(" ");
        cronExpression.append(calendar.get(Calendar.HOUR_OF_DAY)).append(" ");
        cronExpression.append(calendar.get(Calendar.DAY_OF_MONTH)).append(" ");
        cronExpression.append(calendar.get(Calendar.MONTH) + 1).append(" ");
        cronExpression.append("? ");//一个礼拜的第几天 可以忽略
        cronExpression.append(calendar.get(Calendar.YEAR));
        return cronExpression.toString();
    }
}

