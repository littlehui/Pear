package com.pear.commons.tools.quartz.jfinal.init;

import com.jfinal.config.*;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.pear.commons.tools.quartz.router.QuartzRouter;

public class ProjectConfig extends JFinalConfig {

    public void configConstant(Constants me) {
        me.setDevMode(true);
        me.setViewType(ViewType.FREE_MARKER);
        me.setUrlParaSeparator("_");
    }

    public void configRoute(Routes me) {
        me.add("/admin/quartz", QuartzRouter.class);
    }

    public void configPlugin(Plugins me) {
        loadPropertyFile("service_cfg.properties");
/*        DruidPlugin druidPlugin = new DruidPlugin(getProperty("db.url"),getProperty("db.user"),getProperty("db.password"));
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        me.add(arp);*/
        //arp.addMapping("user", User.class);
    }

    public void configInterceptor(Interceptors me) {

    }

    public void configHandler(Handlers me) {

    }

}
