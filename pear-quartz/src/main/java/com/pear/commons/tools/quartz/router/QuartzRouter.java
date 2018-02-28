package com.pear.commons.tools.quartz.router;

import com.jfinal.core.Controller;
import com.pear.commons.tools.quartz.bean.TriggerVo;
import com.pear.commons.tools.quartz.manager.QuartzManager;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import java.util.List;

/**
 * Created by littlehui on 2017/6/6.
 */

public class QuartzRouter extends Controller {

    QuartzManager quartzManager;

    public Response resumeGroup(String group) {
        quartzManager.resumeAll(group);
        return Response.getSuccessResponse();
    }

    public Response delete(String group, String jobName) throws SchedulerException {
        quartzManager.deleteJob(jobName, group);
        return Response.getSuccessResponse();
    }

    public Response resumeJob(String triggerName, String group) {
        quartzManager.resumeTrigger(triggerName, group);
        return Response.getSuccessResponse();
    }

    public Response<List<TriggerVo>> getTriggers(String group) {
        List<TriggerVo> list = quartzManager.getTriggers(group);
        return Response.getSuccessResponse(list);
    }

    public Response<TriggerVo> getTrigger(String name, String group) {
        TriggerVo triggerVo = quartzManager.getTriggerVo(name, group);
        return Response.getSuccessResponse(triggerVo);
    }
}
