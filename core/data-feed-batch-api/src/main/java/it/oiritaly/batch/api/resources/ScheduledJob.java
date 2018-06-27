package it.oiritaly.batch.api.resources;


import org.quartz.JobDetail;
import org.springframework.batch.core.JobExecution;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduledJob {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Trigger {
        private org.quartz.Trigger trigger;
        private org.quartz.Trigger.TriggerState triggerState;
    }

    private JobDetail jobDetail;
    private List<Trigger> triggers;
    private List<JobExecution> lastExecutions;
}