package org.workswap.datasource.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.workswap.datasource.stats.model.ListingView;

public interface ListingViewRepository extends JpaRepository<ListingView, Long>{
    
    ListingView findByUserIdAndListingId(Long userId, Long listingId);

    boolean existsByUserIdAndListingId(Long userId, Long listingId);
}
