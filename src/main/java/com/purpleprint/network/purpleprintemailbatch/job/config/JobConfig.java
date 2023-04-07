package com.purpleprint.network.purpleprintemailbatch.job.config;

import com.purpleprint.network.purpleprintemailbatch.analysis.command.query.service.AnalysisService;
import com.purpleprint.network.purpleprintemailbatch.analysis.command.domain.model.Analysis;
import com.purpleprint.network.purpleprintemailbatch.heart.command.application.dto.RecipientDTO;
import com.purpleprint.network.purpleprintemailbatch.heart.query.service.HeartService;
import com.purpleprint.network.purpleprintemailbatch.job.dto.MailDTO;
import com.purpleprint.network.purpleprintemailbatch.mail.service.MailService;
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
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private final com.purpleprint.network.purpleprintemailbatch.analysis.command.application.service.AnalysisService analysisService2;
    private final HeartService heartService;
    private final UserService userService;
    private final MailService mailService;

    @Value("${job.name}")
    private String BATCH_NAME;

    private List<Analysis> analysisList = new ArrayList<>();
    private List<MailDTO> mailList = new ArrayList<>();

    public JobConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, AnalysisService analysisService,
                     HeartService heartService, UserService userService, MailService mailService,
                     com.purpleprint.network.purpleprintemailbatch.analysis.command.application.service.AnalysisService analysisService2) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.analysisService = analysisService;
        this.analysisService2 = analysisService2;
        this.heartService = heartService;
        this.userService = userService;
        this.mailService = mailService;
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get(BATCH_NAME)
                .start(step1())
                .next(step1())
                .next(step2())
                .next(step3())
                .next(step4())
                .build();
    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get(BATCH_NAME + "step 1")
                .tasklet((stepContribution, chunkContext) -> {
                    log.debug("============ 분석 리스트 호출 =============");
                    analysisList = analysisService.selectAnalysisList();
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get(BATCH_NAME + "step 2")
                .tasklet((stepContribution, chunkContext) -> {
                    log.debug("============ 분석 메일 전송 데이터 호출 =============");
                    mailList = new ArrayList<>();
                    for(Analysis analysis : analysisList) {

                        MailDTO mail = new MailDTO();

                        // 하트 준 사람 호출
                        List<RecipientDTO> giveHeartFriendList = heartService.selectHeartList(analysis.getChild(), analysis.getAnalysisAt());

                        //함께 플레이한 사람 호출
                        List<PlayFriendDTO> playFriendList = userService.selectFriendList(analysis);

                        // 부모 호출
                        User parents = analysis.getChild().getUser();

                        //플레이타임 조회
                        String playTime =userService.selectPlayTime(analysis.getChild().getId(), analysis.getAnalysisAt());

                        //분석일자
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String analysisDate = dateFormat.format(analysis.getAnalysisAt());

                        mail.setChildName(analysis.getChild().getName());
                        mail.setParentsMail(parents.getEmail());
                        mail.setRecipientList(giveHeartFriendList);
                        mail.setPlayFriendList(playFriendList);
                        mail.setPlayPlace(analysis.getPlayPlace());
                        mail.setPlayTime(playTime);
                        mail.setGiveHeartCount(giveHeartFriendList.size());
                        mail.setAnalysisDate(analysisDate);
                        System.out.println(mail);
                        mailList.add(mail);
                    }

                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get(BATCH_NAME + "step 3")
                .tasklet((stepContribution, chunkContext) -> {
                    log.debug("============ 분석 메일 전송 및 전송  =============");

                    mailList.forEach(mail -> {
                        try {
                            mailService.sendMail(mail);
                        } catch (MessagingException e) {
                            throw new RuntimeException(e);
                        }
                    });

                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step4() {
        return stepBuilderFactory.get(BATCH_NAME + "step 4")
                .tasklet((stepBuilderFactory, chunkContext) -> {
                    log.debug("============ 전송 완료여부 변경 =============");

                    for(Analysis analysis : analysisList) {
                        analysisService2.updateAnalysis(analysis);
                    }
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
