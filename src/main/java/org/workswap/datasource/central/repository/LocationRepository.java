package org.workswap.datasource.central.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.workswap.datasource.central.model.listingModels.Location;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findAllByOrderByNameAsc();
    Location findByName(String name);

    List<Location> findByCountry(Location country);
    List<Location> findByCountryId(Long countryId);

    List<Location> findByCity(Boolean city);
}