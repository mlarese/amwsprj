package it.oiritaly.data.models.jpa.rest;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class AmazonFeedSubmission {

    @Transient
    public final String _SUBMITTED_ = ("_SUBMITTED_");
    @Transient
    public final String _SKIPPED_ = ("_SKIPPED_");
    @Transient
    public final String _DONE_ = ("_DONE_");
    @Transient
    public final String _IN_PROGRESS_ = ("_IN_PROGRESS_");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _id;

    @Column(nullable = false, unique = true)
    private String feedSubmissionId;

    @Column(nullable = false)
    private String feedType;

    @Column(nullable = false)
    private String marketplaceId;

    @Column(nullable = false)
    private Date submittedDate;

    private Date startedProcessingDate;

    private Date completedProcessingDate;

    @Column(nullable = false)
    private String feedProcessingStatus;

    @Column
    private Integer messagesProcessed;

    @Column
    private Integer messagesSuccessful;

    @Column
    private Integer messagesWithError;

    @Column
    private Integer messagesWithWarning;

    @Column
    private String feedSubmissionResult;

}
