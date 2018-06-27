package it.oiritaly.batch.configurations;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(InfrastructureConfiguration.class)
public class


SubmissionReportJobConfiguration {

    private final JobBuilderFactory jobs;
    private final StepBuilderFactory steps;

    @Autowired
    public SubmissionReportJobConfiguration(JobBuilderFactory jobs,
                                            StepBuilderFactory steps) {
        this.jobs = jobs;
        this.steps = steps;
    }

    @Bean
    public Job job() {
        return this.jobs
                .get("amazon-submission-report")
                .start(updateFeedSubmissionStatusStep())
                .build();
    }

    @Bean
    @StepScope
    public UpdateFeedSubmissionStatusTasklet updateFeedSubmissionStatusTasklet() {
        return new UpdateFeedSubmissionStatusTasklet();
    }

    @Bean
    public Step updateFeedSubmissionStatusStep() {
        return this.steps
                .get("updateFeedSubmissionStatusStep")
                .tasklet(updateFeedSubmissionStatusTasklet())
                .build();
    }
}
