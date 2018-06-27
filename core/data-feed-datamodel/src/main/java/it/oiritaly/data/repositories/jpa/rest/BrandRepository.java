package it.oiritaly.data.repositories.jpa.rest;

import it.oiritaly.data.models.jpa.rest.Brand;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends PagingAndSortingRepository<Brand, Long> {
}
