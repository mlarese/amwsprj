package it.oiritaly.batch.api.services;

import it.oiritaly.batch.api.exceptions.JobNotFoundException;
import it.oiritaly.batch.api.resources.ScheduleData;
import it.oiritaly.batch.api.resources.ScheduledJob;
import it.oiritaly.batch.scheduling.BatchJobLauncher;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@Transactional
public class BatchApiServiceImpl {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private JobExplorer jobExplorer;

    private Optional<JobInstance> findJobInstanceByJobName(String jobName) {
        return jobExplorer.findJobInstancesByJobName(jobName, 0, 1)
                .stream()
                .findFirst();
    }

    private Optional<JobExecution> findLastJobExecution(JobInstance jobInstance) {
        return jobExplorer.getJobExecutions(jobInstance)
                .stream()
                // filter the first execution TODO (sorting? maybe take last)
                .findFirst();
    }

    private Stream<Trigger> list(GroupMatcher<JobKey> matcher) {
        try {
            return scheduler.getJobKeys(matcher).stream()
                    .flatMap(jobKey -> {
                        try {
                            return scheduler.getTriggersOfJob(jobKey).stream();
                        } catch (SchedulerException e) {
                            throw new RuntimeException("Exception getting job triggers.", e);
                        }
                    });
        } catch (SchedulerException e) {
            throw new RuntimeException("Exception getting job list.", e);
        }

    }

    private JobKey existingJobKey(String name, String group) throws SchedulerException, JobNotFoundException {
        JobKey jobKey = new JobKey(name, group);
        if (!scheduler.checkExists(jobKey)) {
            throw new JobNotFoundException(jobKey);
        }
        return jobKey;
    }

    private ScheduledJob.Trigger toResource(Trigger trigger) {
        try {
            return ScheduledJob.Trigger.builder()
                    .trigger(trigger)
                    .triggerState(scheduler.getTriggerState(trigger.getKey()))
                    .build();
        } catch (SchedulerException e) {
            log.error("Error retrieving trigger state.", e);
            return ScheduledJob.Trigger.builder()
                    .trigger(trigger)
                    .triggerState(Trigger.TriggerState.NONE)
                    .build();
        }
    }

    public Stream<Trigger> listJobsSchedules() throws SchedulerException {
        return list(GroupMatcher.anyJobGroup());
    }

    public Stream<Trigger> listJobsSchedules(String jobGroup) throws SchedulerException {
        return list(GroupMatcher.jobGroupEquals(jobGroup));
    }

    public ScheduledJob getJob(String jobGroup, String jobName) throws SchedulerException, JobNotFoundException {
        JobKey jobKey = existingJobKey(jobName, jobGroup);
        return ScheduledJob.builder()
                .jobDetail(scheduler.getJobDetail(jobKey))
                .triggers(scheduler.getTriggersOfJob(jobKey).stream()
                        .map(this::toResource)
                        .collect(Collectors.toList()))
                .lastExecutions(jobExplorer
                        .getJobNames().stream()
                        .map(this::findJobInstanceByJobName)
                        .filter(Optional::isPresent).map(Optional::get)
                        .map(this::findLastJobExecution)
                        .filter(Optional::isPresent).map(Optional::get)
                        .collect(Collectors.toList()))
                .build();
    }

    public ScheduledJob createJobSchedules(String jobGroup,
                                     String jobName,
                                     @RequestBody @Valid ScheduleData scheduleData) throws SchedulerException, JobNotFoundException {
        // TODO verify that exists a job with scheduleId name
        Set<Trigger> triggers = Arrays.stream(scheduleData.getTriggers())
                .map(trigger -> TriggerBuilder
                        .newTrigger()
                        // TODO Trigger name and group
                        .withIdentity(
                                trigger.getName(),
                                trigger.getGroup())
                        .withDescription(trigger.getDescription())
                        .withSchedule(CronScheduleBuilder
                                .cronSchedule(trigger.getCronExpression())
                                .inTimeZone(trigger.getTimeZone())
                                // TODO MisfireHandlingInstruction
                                .withMisfireHandlingInstructionDoNothing()
                        ).build())
                .collect(Collectors.toSet());
        JobDetail job = JobBuilder
                // TODO Dynamic class?
                .newJob(BatchJobLauncher.class)
                .withIdentity(jobName, jobGroup)
                .withDescription(scheduleData.getJob().getDescription())
                .storeDurably(scheduleData.getJob().isStoreDurably())
                .requestRecovery(scheduleData.getJob().isRequestRecovery())
                .usingJobData(scheduleData.getJob().getJobDataMap())
                .build();
        scheduler.scheduleJob(job, triggers, true);
        return getJob(jobGroup, jobName);
    }

    public void enable() throws SchedulerException {
        scheduler.resumeAll();
    }

    public void enable(String jobGroup) throws SchedulerException {
        scheduler.resumeJobs(GroupMatcher.jobGroupEquals(jobGroup));
    }

    public void enable(String jobGroup, String jobName) throws SchedulerException, JobNotFoundException {
        scheduler.resumeJob(existingJobKey(jobName, jobGroup));
    }

    public void disable() throws SchedulerException {
        scheduler.pauseAll();
    }

    public void disable(String jobGroup) throws SchedulerException {
        scheduler.pauseJobs(GroupMatcher.jobGroupEquals(jobGroup));
    }


    public void disable(String jobGroup, String jobName) throws SchedulerException, JobNotFoundException {
        scheduler.pauseJob(existingJobKey(jobName, jobGroup));
    }

    public void deleteJob(String jobGroup, String jobName) throws SchedulerException, JobNotFoundException {
        scheduler.deleteJob(existingJobKey(jobName, jobGroup));
    }
}
