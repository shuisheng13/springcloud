package com.navinfo.job;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

/**
 * @Author zhaodong
 * @Date 18:38 2019/1/28
 * @Param
 * @return
 **/
@Component
public class MyScheduler {

   @Value("${task.cron.theme.auto.updown}")
   private String autoAuDownTime;

    @Autowired
    SchedulerFactoryBean schedulerFactoryBean;

    static Scheduler scheduler;

    public void scheduleJobs() throws SchedulerException {
        scheduler = schedulerFactoryBean.getScheduler();
        startJob1();
    }

    public void startJob1() {
        JobDetail jobDetail = JobBuilder.newJob(ScheduledJob.class).withIdentity("job2", "group2").build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(autoAuDownTime);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").withSchedule(scheduleBuilder).build();
        try {
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException e) {

        }
    }
}
