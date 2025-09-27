package org.workswap.datasource.stats.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.workswap.common.enums.IntervalType;
import org.workswap.datasource.stats.model.ListingStatSnapshot;

@Repository
public interface ListingStatRepository extends JpaRepository<ListingStatSnapshot, Long> {

    @Query("SELECT COUNT(s) FROM ListingStatSnapshot s WHERE s.listingId = :listingId AND s.intervalType = :intervalType AND s.time >= :since")
    long countRecentSnapshots(@Param("listingId") Long listingId,
                            @Param("intervalType") IntervalType intervalType,
                            @Param("since") LocalDateTime since);

    List<ListingStatSnapshot> findByListingIdAndIntervalTypeAndTimeAfter(
        Long listingId, 
        IntervalType intervalType,
        LocalDateTime time
    );

    Optional<ListingStatSnapshot> findTopByListingIdAndIntervalTypeAndTimeBeforeOrderByTimeDesc(
        Long listingId,
        IntervalType intervalType,
        LocalDateTime time
    );

    @Query("""
        SELECT s FROM ListingStatSnapshot s 
        WHERE s.listingId = :listingId 
            AND s.time BETWEEN :start AND :end 
            AND (:interval IS NULL OR s.intervalType = :interval)
        ORDER BY 
            FUNCTION('IF', :metric = 'views', s.views, NULL) ASC,
            FUNCTION('IF', :metric = 'favorites', s.favorites, NULL) ASC,
            FUNCTION('IF', :metric = 'rating', s.rating, NULL) ASC
        LIMIT 1
    """)
    ListingStatSnapshot findMinByMetric(
        @Param("listingId") Long listingId,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end,
        @Param("interval") IntervalType interval,
        @Param("metric") String metric
    );

    
    @Query("""
        SELECT s FROM ListingStatSnapshot s 
        WHERE s.listingId = :listingId 
          AND s.time BETWEEN :start AND :end 
          AND (:interval IS NULL OR s.intervalType = :interval)

        ORDER BY 
            FUNCTION('IF', :metric = 'views', s.views, NULL) DESC,
            FUNCTION('IF', :metric = 'favorites', s.favorites, NULL) DESC,
            FUNCTION('IF', :metric = 'rating', s.rating, NULL) DESC
        LIMIT 1
    """)
    ListingStatSnapshot findMaxByMetric(
        @Param("listingId") Long listingId,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end,
        @Param("interval") IntervalType interval,
        @Param("metric") String metric
    );
    
    List<ListingStatSnapshot> findAllByListingId(Long listingId);
}
