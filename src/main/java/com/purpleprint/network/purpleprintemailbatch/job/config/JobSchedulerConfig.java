package com.purpleprint.network.purpleprintemailbatch.job.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Class : JobSchedulerConfig
 * Comment: 클래스에 대한 간단 설명
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-21       전현정           최초 생성
 * </pre>
 *
 * @author 전현정(최초 작성자)
 * @version 1(클래스 버전)
 * @see
 */

@Slf4j
@Component
public class JobSchedulerConfig {

    private final JobConfig jobConfig;
    private final JobLauncher jobLauncher;

    @Autowired
    public JobSchedulerConfig(JobConfig jobConfig, JobLauncher jobLauncher) {
        this.jobConfig = jobConfig;
        this.jobLauncher = jobLauncher;
    }

    @Scheduled(cron = "0 0 0-8 * * *")
    public void jobSchdled() {

        Map<String, JobParameter> config = new HashMap<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateTime = new Date();

        config.put("date", new JobParameter(dateFormat.format(dateTime)));

        JobParameters jobParameters = new JobParameters(config);

        try {
            JobExecution jobExecution = jobLauncher.run(jobConfig.job(), jobParameters);
        } catch (JobExecutionAlreadyRunningException e) {
            throw new RuntimeException(e);
        } catch (JobRestartException e) {
            throw new RuntimeException(e);
        } catch (JobInstanceAlreadyCompleteException e) {
            throw new RuntimeException(e);
        } catch (JobParametersInvalidException e) {
            throw new RuntimeException(e);
        }


    }

}
