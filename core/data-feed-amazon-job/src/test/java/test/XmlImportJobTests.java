package test;

import it.oiritaly.batch.configurations.AmazonJobConfiguration;
import it.oiritaly.data.repositories.jpa.DataFeedRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
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
        AmazonJobConfiguration.class})
public class XmlImportJobTests {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;
    @Autowired
    private DataFeedRepository dataFeedRepository;

    @Test
    public void testLaunchJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                // .addString("input", "classpath:OirDataFeed_IT.xml")
                .toJobParameters();
        jobLauncher.run(job, params);

        assertThat(dataFeedRepository.count(), is(1L));
    }
}
