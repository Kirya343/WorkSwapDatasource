package org.workswap.datasource.admin.repository;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.workswap.datasource.admin.model.Task;
import org.workswap.datasource.admin.model.enums.TaskStatus;

@Repository
@Profile("backoffice")
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);
    Task findByName(String name);

    List<Task> findByExecutorId(Long executorId);
}
