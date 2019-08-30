package com.utils.export.Task;

import com.utils.export.service.CronService;
import com.utils.export.utils.ExportZkem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class DynamicScheduleTask implements SchedulingConfigurer {

    @Autowired
    private CronService cronService;

    private static Logger logger = LoggerFactory.getLogger(DynamicScheduleTask.class);

    //连接状态
    private boolean flag = true;

    //cron 表达式
    private String cron = "";

    /**
     * 执行定时任务.
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        ExportZkem exportZkem = new ExportZkem();

        taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> {
                    flag = exportZkem.getConnectZkem();
                    if (flag) {
                        exportZkem.exportZkem();
                    }
                },
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
                    Date now = null;
                    Date beginTime = null;
                    Date endTime = null;
                    try {
                        now = df.parse(df.format(new Date()));
                        beginTime = df.parse("22:00");
                        endTime = df.parse("22:30");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //判断时间是否在指定时间内
                    //在，则判断是否能够成功连接，若不能则执行超时连接
                    if (belongCalendar(now, beginTime, endTime)) {
                        //2.1 从数据库获取执行周期
                        if (flag) {
                            cron = cronService.getCron("export").get("cron").toString();
                        } else {
                            cron = cronService.getCron("outTime").get("cron").toString();
                        }
                    } else {
                        //恢复定时任务周期
                        cron = cronService.getCron("export").get("cron").toString();
                        flag = true;
                    }
                    //2.2 合法性校验.
                    if (StringUtils.isEmpty(cron)) {
                        // Omitted Code ..
                        logger.info("cron数据为空");
                    }
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );
    }

    /**
     * 判断当前时间是否在指定时间内
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }
}
