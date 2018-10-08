package com.cn.sendemail.job;

import com.cn.sendemail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class QuartzSendEmail {

    @Autowired
    private UserService userService;

    @Scheduled(cron = "0/30 * * * * ?")
    public void sendEmail(){
        System.out.println("开始启动定时任务" + new Date());
        userService.sendEmail();
    }
}
