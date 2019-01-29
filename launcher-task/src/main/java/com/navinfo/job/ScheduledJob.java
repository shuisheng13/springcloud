package com.navinfo.job;

import com.navinfo.service.RemoteService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


/**
  * @Author zhaodong
  * @Date 18:38 2019/1/28
  * @Param
  * @return
  **/
 @Slf4j
 @Component
public class ScheduledJob implements Job {

     @Autowired
     private RemoteService remoteService;

     @Override
     public void execute(JobExecutionContext context) {
         log.info("定时执行上下架 start...");
         LocalDate localDate = LocalDate.now();
         remoteService.autoUpDown(localDate.toString());
     }

}
