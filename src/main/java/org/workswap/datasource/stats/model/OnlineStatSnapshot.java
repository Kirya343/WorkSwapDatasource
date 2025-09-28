package org.workswap.datasource.stats.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class OnlineStatSnapshot {

    public OnlineStatSnapshot(
        int online,
        LocalDateTime timestamp
    ) {
        this.online = online;
        this.timestamp = timestamp;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private int online;

    private LocalDateTime timestamp;
}
