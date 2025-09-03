package org.workswap.datasource.central.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.workswap.common.enums.PriceType;
import org.workswap.datasource.central.model.listingModels.Category;
import org.workswap.datasource.central.model.listingModels.Image;
import org.workswap.datasource.central.model.listingModels.ListingTranslation;
import org.workswap.datasource.central.model.listingModels.Location;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
public class Listing {

    public Listing(double price,
                   PriceType priceType,
                   Category category,
                   User author,
                   String imagePath,
                   Location location) {
        this.price = price;
        this.priceType = priceType;
        this.category = category;
        this.author = author;
        this.imagePath = imagePath;
        this.location = location;
    }

    public Listing(User author) {
        this.author = author;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @OneToMany(mappedBy = "listing", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @MapKey(name = "language") 
    private Map<String, ListingTranslation> translations = new HashMap<>();

    @Setter
    @PositiveOrZero
    private double price;

    @Setter
    @Enumerated(EnumType.STRING)
    private PriceType priceType;
    
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @Setter
    private int views = 0;
    
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Setter
    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @OneToMany(mappedBy = "listing", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    @OneToMany(mappedBy = "listing", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @ManyToMany(mappedBy = "favoriteListings")
    private Set<User> favoredByUsers = new HashSet<>();

    @Setter
    private double rating = 0.0;

    @Setter
    private String imagePath;

    // Новые флаги для целевой аудитории
    @ElementCollection
    @CollectionTable(name = "listing_communities", joinColumns = @JoinColumn(name = "listing_id"))
    @Column(name = "language")
    private List<String> communities = new ArrayList<>();

    @Setter
    @ManyToOne
    @JoinColumn(name = "location")
    private Location location;

    @Setter
    private boolean testMode = false;

    @Setter
    private boolean temporary = true;

    @Setter
    @Transient
    private String localizedTitle;

    @Setter
    @Transient
    private String localizedDescription;
}