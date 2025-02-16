package com.ilivanilton.application.task.retrieve.list;

import com.ilivanilton.domain.task.Task;
import com.ilivanilton.domain.task.TaskID;
import com.ilivanilton.domain.task.TaskPriority;

import java.time.Instant;

public record TaskListOutput(
        TaskID id,
        String description,
        TaskPriority priority,
        boolean isActive,
        Instant createdAt,
        Instant deletedAt
) {

    public static TaskListOutput from(final Task aTask) {
        return new TaskListOutput(
                aTask.getId(),
                aTask.getDescription(),
                aTask.getPriority(),
                aTask.isActive(),
                aTask.getCreatedAt(),
                aTask.getDeletedAt()
        );
    }
}
