package org.workswap.datasource.central.repository.listing;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.workswap.datasource.central.model.Listing;
import org.workswap.datasource.central.model.User;
import org.workswap.datasource.central.model.listingModels.Category;
import org.workswap.datasource.central.model.listingModels.Location;

public interface ListingRepository extends JpaRepository<Listing, Long>, ListingRepositoryCustom {
    Page<Listing> findByCategory(String category, Pageable pageable);
    List<Listing> findByAuthor(User author);

    List<Listing> findByAuthorAndActiveTrue(User author);

    List<Listing> findByAuthorAndTemporary(User author, boolean temporary);

    @Query("SELECT l FROM Listing l WHERE l.author.email = :email")
    List<Listing> findByAuthorEmail(@Param("email") String email);

    // Для Page
    @Query("SELECT l FROM Listing l WHERE l.active = true")
    Page<Listing> findPageByActiveTrue(Pageable pageable);
    // Для List
    @Query("SELECT l FROM Listing l WHERE l.active = true")
    List<Listing> findListByActiveTrue();

    List<Listing> findListByActiveTrueAndTestModeFalse();

    Page<Listing> findByCategoryAndActiveTrue(String category, Pageable pageable);
    
    @Query("SELECT l FROM Listing l WHERE l.active = true AND l.category = :category")
    Page<Listing> findActiveByCategory(@Param("category") String category, Pageable pageable);

    @Query("SELECT l FROM Listing l WHERE l.category = :category AND l.id != :excludeId AND l.active = true ORDER BY l.createdAt DESC")
    List<Listing> findByCategoryAndIdNot(@Param("category") Category category, @Param("excludeId") Long excludeId, Pageable pageable);

    @Query("SELECT l FROM Listing l WHERE l.category IN :categories")
    List<Listing> findByCategories(@Param("categories") List<Category> categories);
    
    @Query("SELECT l FROM Listing l WHERE l.location IN :locations")
    List<Listing> findByLocations(@Param("locations") List<Location> locations);

    @Query("SELECT l FROM Listing l JOIN l.communities c WHERE c = :language AND l.active = true ")
    List<Listing> findByCommunityAndActiveTrue(@Param("language") String language);

    @Query("SELECT DISTINCT l FROM Listing l JOIN l.communities c WHERE c IN :languages AND l.active = true AND l.temporary = false")
    List<Listing> findByCommunitiesInAndActiveTrue(@Param("languages") List<String> languages);

    // Новый метод для оптимизированной загрузки
    @Query("SELECT DISTINCT l FROM Listing l " +
            "LEFT JOIN FETCH l.author " +
            "LEFT JOIN FETCH l.reviews " +
            "WHERE l.id = :id")
    Optional<Listing> findByIdWithAuthorAndReviews(@Param("id") Long id);

    @Query("SELECT SUM(l.views) FROM Listing l")
    Integer sumViews();

    @Query("SELECT COUNT(l) FROM Listing l WHERE l.active = true")
    long countActiveListings();

    Page<Listing> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Query("""
        SELECT DISTINCT l FROM Listing l
        JOIN l.translations t
        WHERE (
            LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%')) OR
            LOWER(t.description) LIKE LOWER(CONCAT('%', :query, '%'))
        )
        AND l.active = true
    """)
    List<Listing> searchAllFields(@Param("query") String query);

    @Query(value = "select count(*) from favorite_listing where listing_id = :listingId", nativeQuery = true)
    int countFavoritesByListingId(@Param("listingId") Long listingId);
}
