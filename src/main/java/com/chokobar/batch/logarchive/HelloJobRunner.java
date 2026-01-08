package com.chokobar.batch.logarchive;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class HelloJobRunner implements CommandLineRunner {

    private final JobLauncher jobLauncher;
    private final Job helloJob;

    public HelloJobRunner(JobLauncher jobLauncher, Job helloJob) {
        this.jobLauncher = jobLauncher;
        this.helloJob = helloJob;
    }

    @Override
    public void run(String... args) throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addString("runAt", LocalDateTime.now().toString())
                .toJobParameters();

        jobLauncher.run(helloJob, params);
    }
}
