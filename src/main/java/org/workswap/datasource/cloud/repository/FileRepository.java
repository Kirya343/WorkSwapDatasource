package org.workswap.datasource.cloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.workswap.datasource.cloud.model.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    
    File findByFileName(String fileName);
    File findByStoragePath(String storagePath);

    void deleteByStoragePath(String storagePath);
}
