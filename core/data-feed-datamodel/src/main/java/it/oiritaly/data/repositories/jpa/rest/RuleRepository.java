package it.oiritaly.data.repositories.jpa.rest;

import it.oiritaly.data.models.jpa.rest.Rule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RuleRepository extends PagingAndSortingRepository<Rule, Long> {
    Page<Rule> findAllByMarketplaceId(@Param("marketplaceId") String marketplaceId, Pageable pageable);
    List<Rule> findAllByFromDateLessThanEqualAndToDateGreaterThanEqualAndIsActiveTrue(@Param("from") Date from, @Param("to") Date to);
}
