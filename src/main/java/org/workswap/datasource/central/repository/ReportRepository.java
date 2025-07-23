package org.workswap.datasource.central.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.workswap.datasource.central.model.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
    
}
