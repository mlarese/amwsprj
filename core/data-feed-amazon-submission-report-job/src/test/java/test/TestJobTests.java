package test;

import it.oiritaly.batch.configurations.SubmissionReportJobConfiguration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {
        BatchTestConfiguration.class,
        SubmissionReportJobConfiguration.class})
public class TestJobTests {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;


    @Test
    public void testLaunchJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                // .addString("input", "classpath:OirDataFeed_IT.xml")
                .toJobParameters();
        JobExecution execution = jobLauncher.run(job, params);

        assertThat(execution.getStatus(), is(BatchStatus.COMPLETED));
    }
}
