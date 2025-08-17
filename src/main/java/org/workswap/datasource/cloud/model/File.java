package org.workswap.datasource.cloud.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.workswap.common.enums.Visibility;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class File {

    public File(
        String fileName,
        String fileExtension,
        String mimeType,
        String checksum,
        Visibility visibility,
        Long size,
        String storagePath,
        String ownerSub
    ) {
        this.fileName = fileName;
        this.fileExtension = fileExtension;
        this.mimeType = mimeType;
        this.checksum = checksum;
        this.visibility = visibility;
        this.size = size;
        this.storagePath = storagePath;
        this.ownerSub = ownerSub;
        this.lastAccessed = LocalDateTime.now();
    }
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String fileExtension;

    private String mimeType;

    private String checksum;

    private Long size;

    private String storagePath;

    private String ownerSub;

    @Setter
    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Setter
    private LocalDateTime lastAccessed;
}
