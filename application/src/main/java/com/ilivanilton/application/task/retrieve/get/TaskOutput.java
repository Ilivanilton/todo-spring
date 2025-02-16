package com.ilivanilton.application.task.retrieve.get;

import com.ilivanilton.domain.task.Task;
import com.ilivanilton.domain.task.TaskID;
import com.ilivanilton.domain.task.TaskPriority;

import java.time.Instant;

public record TaskOutput(
        TaskID id,
        String description,
        TaskPriority priority,
        boolean isActive,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {

    public static TaskOutput from(final Task aTask){
        return new TaskOutput(
                aTask.getId(),
                aTask.getDescription(),
                aTask.getPriority(),
                aTask.isActive(),
                aTask.getCreatedAt(),
                aTask.getUpdatedAt(),
                aTask.getDeletedAt()
        );
    }
}

