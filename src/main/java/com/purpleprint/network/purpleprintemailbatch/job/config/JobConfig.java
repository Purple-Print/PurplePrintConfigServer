package com.purpleprint.network.purpleprintemailbatch.job.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 * Class : JobConfig
 * Comment: 클래스에 대한 간단 설명
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-20       전현정           최초 생성
 * </pre>
 *
 * @author 전현정(최초 작성자)
 * @version 1(클래스 버전)
 * @see
 */

@Slf4j
@Configuration
public class JobConfig {
    //JOB = 배치 작업 단위, SETP = JOB 묶, STEP 종류 = tasklet, reader + processor + writer

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Value("${job.name}")
    private String BATCH_NAME;

    public JobConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get(BATCH_NAME)
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get(BATCH_NAME + "step 1")
                .tasklet((stepContribution, chunkContext) -> {
                    log.debug("============ 전송 시작 =============");
                    System.out.println("첫번째 테스트");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get(BATCH_NAME + "step 2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("두번째 테스트");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
