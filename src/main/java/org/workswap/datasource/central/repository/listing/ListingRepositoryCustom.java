package org.workswap.datasource.central.repository.listing;

import java.util.List;

import org.workswap.datasource.central.model.Listing;
import org.workswap.datasource.central.model.listingModels.Category;

public interface ListingRepositoryCustom {
    public List<Listing> findListings(
        List<Category> categories,
        String locationName, 
        String search,
        Boolean requireReviews,
        List<String> languages,
        int page,
        int size);
}
