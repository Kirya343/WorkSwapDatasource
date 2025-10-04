package org.workswap.datasource.stats.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.workswap.datasource.stats.model.OnlineStatSnapshot;

@Repository
public interface OnlineStatRepository extends JpaRepository<OnlineStatSnapshot, Long> {

    List<OnlineStatSnapshot> findByTimestampAfter(LocalDateTime dateTime);

    OnlineStatSnapshot findFirstByOrderByTimestampDesc();
}
