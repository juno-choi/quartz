package com.kakaovx.batchstat.reader.quartz;

import lombok.RequiredArgsConstructor;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class BoardSimpleQuartz extends QuartzJobBean {
    private final Job boardSimpleJob;
    private final JobExplorer jobExplorer;
    private final JobLauncher jobLauncher;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String date = now.format(formatter);

        JobParameters jobParameters = new JobParametersBuilder(this.jobExplorer)
                .addString("version", "1")
                .addString("date", date)
                .toJobParameters();

        try {
            this.jobLauncher.run(boardSimpleJob, jobParameters);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
