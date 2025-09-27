package org.workswap.datasource.stats.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ListingView {

    public ListingView(
        Long userId,
        Long listingId
    ) {
        this.userId = userId;
        this.listingId = listingId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long userId;

    private Long listingId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
