package it.oiritaly.batch.scheduling;

import org.quartz.JobExecutionContext;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BatchJobLauncher extends QuartzJobBean {

    @Autowired
    private JobLocator jobLocator;
    @Autowired
    private JobLauncher jobLauncher;

//    @Autowired
//    public BatchJobLauncher(JobLocator jobLocator, JobLauncher jobLauncher) {
//        this.jobLocator = jobLocator;
//        this.jobLauncher = jobLauncher;
//    }


    //get params from jobDataAsMap property, job-quartz.xml
    private JobParameters getJobParametersFromJobMap(Map<String, Object> jobDataMap) {
        JobParametersBuilder builder = new JobParametersBuilder();
        for (Map.Entry<String, Object> entry : jobDataMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                builder.addString(key, (String) value);
            } else if (value instanceof Float || value instanceof Double) {
                builder.addDouble(key, ((Number) value).doubleValue());
            } else if (value instanceof Integer || value instanceof Long) {
                builder.addLong(key, ((Number) value).longValue());
            } else if (value instanceof Date) {
                builder.addDate(key, (Date) value);
            } else {
                // JobDataMap contains values which are not job parameters
                // (ignoring)
            }
        }
        //need unique job parameter to rerun the completed job
        builder.addString("uuid", UUID.randomUUID().toString());
        return builder.toJobParameters();

    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws org.quartz.JobExecutionException {
        String jobName = context.getJobDetail().getKey().getName();
        Map<String, Object> jobDataMap = context.getMergedJobDataMap();
        JobParameters jobParameters = getJobParametersFromJobMap(jobDataMap);
        try {
            jobLauncher.run(jobLocator.getJob(jobName), jobParameters);
        } catch (JobExecutionException e) {
            log.error("Error launching Spring Batch job.", e);
        }

    }
}
