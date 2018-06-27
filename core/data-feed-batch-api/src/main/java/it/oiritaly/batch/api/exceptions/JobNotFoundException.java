package it.oiritaly.batch.api.exceptions;

import org.quartz.JobKey;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class JobNotFoundException extends Exception {
    public JobNotFoundException(JobKey jobKey) {
        super(String.format("Job with JobKey=%s not found.", jobKey));
    }
}
