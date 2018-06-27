package it.oiritaly.batch.configurations;

import com.amazonaws.mws.model.FeedSubmissionInfo;
import it.oiritaly.batch.api.mws.GetFeedSubmissionResult;
import it.oiritaly.batch.api.mws.ListFeedSubmissions;
import it.oiritaly.batch.api.mws.configuration.MwsConfigurationProperties;
import it.oiritaly.data.models.amazon.ProcessingReport;
import it.oiritaly.data.models.jpa.rest.AmazonFeedSubmission;
import it.oiritaly.data.repositories.jpa.rest.AmazonFeedSubmissionRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;


@Slf4j
@Data
public class UpdateFeedSubmissionStatusTasklet implements Tasklet {

    @Autowired
    private ListFeedSubmissions listFeedSubmissions;

    @Autowired
    private GetFeedSubmissionResult getFeedSubmissionResult;

    @Autowired
    private AmazonFeedSubmissionRepository amazonFeedSubmissionRepository;

    @Autowired
    private MwsConfigurationProperties configurationProperties;


    public UpdateFeedSubmissionStatusTasklet() {

    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.debug("Finding the oldest not processed feed");
        AmazonFeedSubmission afs = amazonFeedSubmissionRepository.findTop1ByFeedProcessingStatusNotLikeOrderBySubmittedDateAsc("_DONE_");

        if (afs != null) {
            log.info("Feed " + afs.getFeedSubmissionId() + " not processed since " + afs.getSubmittedDate());
            DateTime dt = new DateTime(afs.getSubmittedDate());
            if (isFeedSubmissionTooOld(dt)) {
                log.info("Feed " + afs.getFeedSubmissionId() + " is too old, abort update");
                afs.setFeedProcessingStatus(afs._SKIPPED_);
            } else {

                FeedSubmissionInfo fsi = listFeedSubmissions.getFeedSubmissionInfo(configurationProperties.getMwsEndpoint(), afs.getFeedSubmissionId());

                String status = fsi.getFeedProcessingStatus();

                if (!status.equals(afs._SUBMITTED_)) {
                    afs.setFeedProcessingStatus(status);
                    afs.setStartedProcessingDate(fsi.getStartedProcessingDate().toGregorianCalendar().getTime());

                    if (status.equals(afs._DONE_)) {
                        afs.setCompletedProcessingDate(fsi.getCompletedProcessingDate().toGregorianCalendar().getTime());

                        String fileName = getFeedSubmissionResult.writeFeedSubmissionResult(configurationProperties.getMwsEndpoint(), fsi.getFeedSubmissionId());

                        File file = new File(fileName);

                        while (!isCompletelyWritten(file)) {
                            Thread.sleep(3000);
                        }

                        ProcessingReport.ProcessingSummary summary = getFeedSubmissionResult.parseFeedSubmissionResultFile(fileName);

                        afs.setFeedSubmissionResult(fileName);

                        afs.setMessagesProcessed(summary.getMessagesProcessed().intValue());
                        afs.setMessagesSuccessful(summary.getMessagesSuccessful().intValue());
                        afs.setMessagesWithError(summary.getMessagesWithError().intValue());
                        afs.setMessagesWithWarning(summary.getMessagesWithWarning().intValue());
                    }
                }
            }

            amazonFeedSubmissionRepository.save(afs);

        }

        return RepeatStatus.FINISHED;

    }

    private boolean isFeedSubmissionTooOld(DateTime datetimeSubmitted) {
        Period maxPeriod = new Period().withHours(24);
        return datetimeSubmitted.plus(maxPeriod).isBeforeNow();
    }

    private boolean isCompletelyWritten(File file) throws InterruptedException {
        Long fileSizeBefore = file.length();
        Thread.sleep(3000);
        Long fileSizeAfter = file.length();

        log.debug("comparing file size " + fileSizeBefore + " with " + fileSizeAfter);

        if (fileSizeBefore.equals(fileSizeAfter)) {
            return true;
        }
        return false;
    }
}