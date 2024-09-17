package com.ilivanilton.application.task.retrieve.get;

import com.ilivanilton.domain.task.Task;
import com.ilivanilton.domain.task.TaskID;

import java.time.Instant;

public record TaskOutput(
        TaskID id,
        String description,
        boolean isActive,
        Instant createdAt,
        Instant deletedAt
) {

    public static TaskOutput from(final Task aTask){
        return new TaskOutput(
                aTask.getId(),
                aTask.getDescription(),
                aTask.isActive(),
                aTask.getCreatedAt(),
                aTask.getDeletedAt()
        );
    }
}

