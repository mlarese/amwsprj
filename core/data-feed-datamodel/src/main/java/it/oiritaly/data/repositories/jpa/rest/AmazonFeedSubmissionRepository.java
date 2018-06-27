package it.oiritaly.data.repositories.jpa.rest;

import it.oiritaly.data.models.jpa.rest.AmazonFeedSubmission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmazonFeedSubmissionRepository extends PagingAndSortingRepository<AmazonFeedSubmission, Long> {
    AmazonFeedSubmission findTop1ByFeedProcessingStatusNotLikeOrderBySubmittedDateAsc(@Param("status") String status);
    AmazonFeedSubmission findTopByFeedSubmissionId(@Param("feedSubmissionId") String feedSubmissionId);
    Page<AmazonFeedSubmission> findAllByMarketplaceId(@Param("marketplaceId") String marketplaceId, Pageable pageable);
}
