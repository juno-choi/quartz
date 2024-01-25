package com.kakaovx.batchstat.tasklet.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class TestJob {
    private final PlatformTransactionManager transactionManager;
    private final JobRepository jobRepository;
    @Bean
    public Job testSimpleJob(){
        return new JobBuilder("testSimpleJob", jobRepository)
                .start(testSimpleStep())
                .build();
    }

    @Bean
    public Step testSimpleStep(){
        return new StepBuilder("testSimpleStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("test simple");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

}
