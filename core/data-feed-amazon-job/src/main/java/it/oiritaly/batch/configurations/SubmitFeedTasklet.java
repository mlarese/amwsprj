package it.oiritaly.batch.configurations;

import com.amazonaws.mws.model.FeedSubmissionInfo;
import com.amazonaws.mws.model.IdList;
import com.amazonaws.mws.model.SubmitFeedResponse;
import it.oiritaly.batch.api.mws.GetFeedSubmissionResult;
import it.oiritaly.batch.api.mws.ListFeedSubmissions;
import it.oiritaly.batch.api.mws.SubmitFeedAsync;
import it.oiritaly.batch.api.mws.configuration.MwsConfigurationProperties;
import it.oiritaly.batch.api.mws.model.SubmitFeedRequest;
import it.oiritaly.data.models.amazon.ProcessingReport;
import it.oiritaly.data.models.jpa.rest.AmazonFeedSubmission;
import it.oiritaly.data.repositories.jpa.rest.AmazonFeedSubmissionRepository;
import lombok.Data;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

@Slf4j
@Data
public class SubmitFeedTasklet implements Tasklet {

    private final String marketplaceId;
    private final SubmitFeedRequest.FeedType feedType;

    @Autowired
    private MwsConfigurationProperties configurationProperties;

    @Autowired
    private SubmitFeedAsync submitFeedAsync;

    @Autowired
    private AmazonFeedSubmissionRepository amazonFeedSubmissionRepository;

    @Autowired
    private GetFeedSubmissionResult getFeedSubmissionResult;

    @Autowired
    private ListFeedSubmissions listFeedSubmissions;

    public SubmitFeedTasklet(String marketplaceId, SubmitFeedRequest.FeedType feedType) {
        this.marketplaceId = marketplaceId;
        this.feedType = feedType;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        SubmitFeedRequest.FeedType ft = feedType;

        IdList idList = new IdList();

        List<String> ids = new ArrayList<>();

        ids.add(marketplaceId);

        idList.setId(ids);

        String serviceUrl = configurationProperties.getMwsEndpoint();

        List<Future<SubmitFeedResponse>> responses = submitFeedAsync.init(serviceUrl, ft, idList);


        for (Future<SubmitFeedResponse> future : responses) {

            while (!future.isDone()) {
                Thread.yield();
            }

            SubmitFeedResponse response = future.get();

            AmazonFeedSubmission afs = new AmazonFeedSubmission();
            afs.setFeedSubmissionId(response.getSubmitFeedResult().getFeedSubmissionInfo().getFeedSubmissionId());
            afs.setFeedType(response.getSubmitFeedResult().getFeedSubmissionInfo().getFeedType());
            afs.setMarketplaceId(marketplaceId);
            afs.setSubmittedDate(new Date());
            afs.setFeedProcessingStatus(response.getSubmitFeedResult().getFeedSubmissionInfo().getFeedProcessingStatus());

            amazonFeedSubmissionRepository.save(afs);


            FeedSubmissionInfo fsi = listFeedSubmissions.getFeedSubmissionInfo(configurationProperties.getMwsEndpoint(), afs.getFeedSubmissionId());
            String status = fsi.getFeedProcessingStatus();

            while (!status.equals(afs._DONE_) ) {
                afs.setFeedProcessingStatus(status);
                if(fsi.getStartedProcessingDate() != null) {
                    afs.setStartedProcessingDate(fsi.getStartedProcessingDate().toGregorianCalendar().getTime());
                }

                amazonFeedSubmissionRepository.save(afs);

                // max one request per minute
                Thread.sleep(61000);

                fsi = listFeedSubmissions.getFeedSubmissionInfo(configurationProperties.getMwsEndpoint(), afs.getFeedSubmissionId());
                status = fsi.getFeedProcessingStatus();
            }


            String fileName = getFeedSubmissionResult.writeFeedSubmissionResult(configurationProperties.getMwsEndpoint(), fsi.getFeedSubmissionId());

            File file = new File(fileName);

            while (!isCompletelyWritten(file)) {
                Thread.sleep(3000);
            }

            ProcessingReport.ProcessingSummary summary = getFeedSubmissionResult.parseFeedSubmissionResultFile(fileName);

            afs.setFeedSubmissionResult(fileName);
            afs.setFeedProcessingStatus(status);
            afs.setCompletedProcessingDate(fsi.getCompletedProcessingDate().toGregorianCalendar().getTime());
            afs.setMessagesProcessed(summary.getMessagesProcessed().intValue());
            afs.setMessagesSuccessful(summary.getMessagesSuccessful().intValue());
            afs.setMessagesWithError(summary.getMessagesWithError().intValue());
            afs.setMessagesWithWarning(summary.getMessagesWithWarning().intValue());
            amazonFeedSubmissionRepository.save(afs);

        }

        return RepeatStatus.FINISHED;
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
