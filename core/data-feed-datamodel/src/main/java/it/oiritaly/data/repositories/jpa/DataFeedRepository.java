package it.oiritaly.data.repositories.jpa;

import it.oiritaly.data.models.jpa.DataFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataFeedRepository extends JpaRepository<DataFeed, Long> {

    DataFeed findTop1ByOrderByParseDateDateTimeDesc();

}
