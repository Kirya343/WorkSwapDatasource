package org.workswap.datasource.admin.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.workswap.datasource.admin.model.enums.TaskStatus;
import org.workswap.datasource.admin.model.enums.TaskType;
import org.workswap.datasource.central.model.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
public class Task {

    public Task(String name,
                String description,
                LocalDateTime deadline,
                TaskType taskType,
                Long authorId) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.taskType = taskType;
        this.authorId = authorId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 2000)
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime deadline;

    @Setter
    private LocalDateTime completed;

    @Setter
    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.NEW;

    @Setter
    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    @Setter
    @Transient
    private User executor;

    @Setter
    @Transient
    private User author;

    @Setter
    private Long executorId;

    private Long authorId;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskComment> comments = new ArrayList<>(); 

    @Transient // чтобы Hibernate не пытался сохранять это поле в БД
    public Duration getDuration() {
        if (deadline != null) {
            return Duration.between(LocalDateTime.now(), deadline);
        }
        return null;
    }
}
