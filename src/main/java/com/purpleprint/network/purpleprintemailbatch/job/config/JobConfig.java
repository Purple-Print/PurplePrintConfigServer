package com.purpleprint.network.purpleprintemailbatch.job.config;

import com.purpleprint.network.purpleprintemailbatch.analysis.command.query.service.AnalysisService;
import com.purpleprint.network.purpleprintemailbatch.analysis.command.domain.model.Analysis;
import com.purpleprint.network.purpleprintemailbatch.heart.command.application.dto.RecipientDTO;
import com.purpleprint.network.purpleprintemailbatch.heart.query.service.HeartService;
import com.purpleprint.network.purpleprintemailbatch.user.command.application.dto.PlayFriendDTO;
import com.purpleprint.network.purpleprintemailbatch.user.command.domain.domain.User;
import com.purpleprint.network.purpleprintemailbatch.user.query.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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
    private final AnalysisService analysisService;
    private final HeartService heartService;
    private final UserService userService;

    @Value("${job.name}")
    private String BATCH_NAME;

    private List<Analysis> analysisList;

    public JobConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, AnalysisService analysisService, HeartService heartService, UserService userService) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.analysisService = analysisService;
        this.heartService = heartService;
        this.userService = userService;
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
                    log.debug("============ 분석 리스트 호출 =============");
                    System.out.println("1. 분석 리스트 호출");
                    analysisList = analysisService.selectAnalysisList();
                    System.out.println(analysisList);
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get(BATCH_NAME + "step 2")
                .tasklet((stepContribution, chunkContext) -> {
                    log.debug("============ 분석 메일 전송 데이터 호출 =============");

                    analysisList.forEach(analysis -> {
                        // 하트 준 사람 호출
                        List<RecipientDTO> giveHeartFriendList = heartService.selectHeartList(analysis.getChild());

                        //함께 플레이한 사람 호출
                        List<PlayFriendDTO> playFriendList = userService.selectFriendList(analysis);

                        // 부모 호출
                        User parents = analysis.getChild().getUser();


                    });

                    return RepeatStatus.FINISHED;
                }).build();
    }
}
