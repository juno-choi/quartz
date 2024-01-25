package com.kakaovx.batchstat.reader.quartz;

import org.quartz.*;
import org.quartz.spi.MutableTrigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class BoardQuartzConfiguration {

    @Bean
    public JobDetail boardQuartzJobDetail() {
        return JobBuilder.newJob(BoardSimpleQuartz.class)
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger boardJobTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder
                .cronSchedule("0 0/1 * * * ?");

        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(10)
                .withRepeatCount(3);

        return TriggerBuilder.newTrigger()
                .forJob(boardQuartzJobDetail())
                .withSchedule(cronScheduleBuilder)
                .build();

    }
}
