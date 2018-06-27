package it.oiritaly.batch.api.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "scheduling.quartz")
public class QuartzConfigurationProperties {
    private Boolean overwriteExistingJobs = true;
    private Boolean waitForJobsToCompleteOnShutdown = false;
    private File databaseInitScript;
}

