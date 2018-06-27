package it.oiritaly.batch.api.controllers;

import it.oiritaly.batch.api.exceptions.JobNotFoundException;
import it.oiritaly.batch.api.resources.ScheduleData;
import it.oiritaly.batch.api.resources.ScheduledJob;
import it.oiritaly.batch.api.services.BatchApiServiceImpl;

import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/schedules")
public class SchedulingController {

    @Autowired
    private BatchApiServiceImpl service;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Trigger> listJobsSchedules() throws SchedulerException {
        return service.listJobsSchedules().collect(Collectors.toList());
    }

    @RequestMapping(value = "/{jobGroup}", method = RequestMethod.GET)
    public List<Trigger> listJobsSchedules(@PathVariable String jobGroup) throws SchedulerException {
        return service.listJobsSchedules(jobGroup).collect(Collectors.toList());
    }

    @RequestMapping(value = "/{jobGroup}/{jobName}", method = RequestMethod.GET)
    public ScheduledJob getJob(@PathVariable String jobGroup,
                               @PathVariable String jobName) throws SchedulerException, JobNotFoundException {
        return service.getJob(jobGroup, jobName);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{jobGroup}/{jobName}", method = RequestMethod.POST)
    public ScheduledJob createJobSchedules(@PathVariable String jobGroup,
                                     @PathVariable String jobName,
                                     @RequestBody @Valid ScheduleData scheduleData) throws SchedulerException, JobNotFoundException {
        return service.createJobSchedules(jobGroup, jobName, scheduleData);
    }


    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    public void enable() throws SchedulerException {
        service.enable();
    }


    @RequestMapping(value = "/{jobGroup}/enable", method = RequestMethod.POST)
    public void enable(@PathVariable String jobGroup) throws SchedulerException {
        service.enable(jobGroup);
    }

    @RequestMapping(value = "/{jobGroup}/{jobName}/enable", method = RequestMethod.POST)
    public void enable(@PathVariable String jobGroup,
                       @PathVariable String jobName) throws SchedulerException, JobNotFoundException {
        service.enable(jobGroup, jobName);
    }

    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    public void disable() throws SchedulerException {
        service.disable();
    }

    @RequestMapping(value = "/{jobGroup}/disable", method = RequestMethod.POST)
    public void disable(@PathVariable String jobGroup) throws SchedulerException {
        service.disable(jobGroup);
    }


    @RequestMapping(value = "/{jobGroup}/{jobName}/disable", method = RequestMethod.POST)
    public void disable(@PathVariable String jobGroup,
                        @PathVariable String jobName) throws SchedulerException, JobNotFoundException {
        service.disable(jobGroup, jobName);
    }

    @RequestMapping(value = "/{jobGroup}/{jobName}", method = RequestMethod.DELETE)
    public void deleteJob(@PathVariable String jobGroup,
                          @PathVariable String jobName) throws SchedulerException, JobNotFoundException {
        service.deleteJob(jobGroup, jobName);
    }
}
