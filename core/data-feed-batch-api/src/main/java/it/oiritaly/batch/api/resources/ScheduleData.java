package it.oiritaly.batch.api.resources;


import org.hibernate.validator.constraints.NotEmpty;
import org.quartz.JobDataMap;

import java.util.TimeZone;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ScheduleData {
    @Data
    @NoArgsConstructor
    public static class Trigger {
        @NotNull
        @NotEmpty
        private String name;
        private String group;
        @NotNull
        @NotEmpty
        private String cronExpression;
        private String description;
        private TimeZone timeZone;
        // TODO MisfireHandlingInstruction
    }

    @Data
    @NoArgsConstructor
    public static class Job {
        private JobDataMap jobDataMap = new JobDataMap();
        private String description;
        private boolean storeDurably = true;
        private boolean requestRecovery = true;
    }

    @NotNull
    private Trigger[] triggers;
    private Job job = new Job();
}