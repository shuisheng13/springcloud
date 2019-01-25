package com.navinfo.task;

import com.navinfo.service.RemoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName Task
 * @Description
 * @Author xukj
 * @Date 2019/1/24 20:00
 * @Version
 */
@Component
@Slf4j
public class Task {


    @Autowired
    private RemoteService remoteService;

    @Scheduled(cron = "${task.cron.theme.auto.updown}")
    public void task() {
        log.info("定时执行上下架 start...");
        remoteService.autoUpDown();
        log.info("定时执行上下架 end...");
    }
}
