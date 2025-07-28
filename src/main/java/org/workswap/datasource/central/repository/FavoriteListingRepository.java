package org.workswap.datasource.central.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.workswap.datasource.central.model.Listing;
import org.workswap.datasource.central.model.User;
import org.workswap.datasource.central.model.listingModels.FavoriteListing;

import java.util.List;

public interface FavoriteListingRepository extends JpaRepository<FavoriteListing, Long> {
    List<FavoriteListing> findByUser(User user);
    boolean existsByUserAndListing(User user, Listing listing);
    void deleteByUserAndListing(User user, Listing listing);
    FavoriteListing findByUserAndListing(User user, Listing listing);
}
