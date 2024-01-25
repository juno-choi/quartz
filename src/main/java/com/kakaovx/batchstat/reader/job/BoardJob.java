package com.kakaovx.batchstat.reader.job;

import com.kakaovx.batchstat.reader.domain.vo.BoardStatisticsVo;
import com.kakaovx.batchstat.reader.domain.vo.BoardVo;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BoardJob {
    private final PlatformTransactionManager transactionManager;
    private final JobRepository jobRepository;
    private final SqlSessionFactory sqlSessionFactory;
    private final int CHUNK_SIZE = 100;
    @Bean
    public Job boardSimpleJob(){
        return new JobBuilder("boardSimpleJob", jobRepository)
                .start(boardSimpleStep())
                .build();
    }

    @Bean
    @JobScope
    public Step boardSimpleStep(){
        return new StepBuilder("boardSimpleStep", jobRepository)
                .<BoardVo, BoardStatisticsVo>chunk(CHUNK_SIZE, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public MyBatisPagingItemReader<BoardVo> reader() {
        MyBatisPagingItemReader<BoardVo> reader = new MyBatisPagingItemReader<>();
        reader.setSqlSessionFactory(sqlSessionFactory);
        reader.setPageSize(CHUNK_SIZE);
        reader.setQueryId("com.kakaovx.batchstat.reader.domain.mapper.BoardMapper.selectBoard");

        return reader;
    }

    @Bean
    @StepScope
    public ItemProcessor<BoardVo, BoardStatisticsVo> processor() {
        return new ItemProcessor<BoardVo, BoardStatisticsVo>() {
            @Override
            public BoardStatisticsVo process(BoardVo item) throws Exception {
                String name = item.getName() + item.getId();
                return BoardStatisticsVo.builder().name(name).build();
            }
        };
    }

    @Bean
    @StepScope
    public MyBatisBatchItemWriter<BoardStatisticsVo> writer() {
        MyBatisBatchItemWriter<BoardStatisticsVo> writer = new MyBatisBatchItemWriter<>();
        writer.setSqlSessionFactory(sqlSessionFactory);
        writer.setStatementId("com.kakaovx.batchstat.reader.domain.mapper.BoardMapper.insertBoard");
        return writer;
    }
}
