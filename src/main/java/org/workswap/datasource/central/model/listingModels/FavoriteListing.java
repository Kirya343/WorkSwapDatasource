package org.workswap.datasource.central.model.listingModels;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.workswap.datasource.central.model.Listing;
import org.workswap.datasource.central.model.User;

@Getter
@Entity
@NoArgsConstructor
public class FavoriteListing {

    public FavoriteListing(User user,
                           Listing listing) {
        this.user = user;
        this.listing = listing;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Listing listing;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
