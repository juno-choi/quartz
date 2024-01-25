package com.kakaovx.batchstat.tasklet.quartz;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class TestQuartzConfiguration {

    @Bean
    public JobDetail testQuartzJobDetail() {
        return JobBuilder.newJob(TestSimpleQuartz.class)
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger testJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(1)
                .withRepeatCount(100);

        return TriggerBuilder.newTrigger()
                .forJob(testQuartzJobDetail())
                .withSchedule(scheduleBuilder)
                .startAt(new Date(System.currentTimeMillis() + 30000)) // 30초 후 시작
                .build();

    }
}
