package com.navinfo.job;

import com.navinfo.service.RemoteService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.time.Instant;



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
     public void execute(JobExecutionContext context) throws JobExecutionException {
         log.info("定时执行上下架 start...");
         remoteService.autoUpDown(Instant.now().getEpochSecond());
     }

}
