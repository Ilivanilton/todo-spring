package com.ilivanilton.application.task.retrieve.list;

import com.ilivanilton.domain.task.Task;
import com.ilivanilton.domain.task.TaskID;

import java.time.Instant;

public record TaskListOutput(
        TaskID id,
        String description,
        boolean isActive,
        Instant createdAt,
        Instant deletedAt
) {

    public static TaskListOutput from(final Task aCategory) {
        return new TaskListOutput(
                aCategory.getId(),
                aCategory.getDescription(),
                aCategory.isActive(),
                aCategory.getCreatedAt(),
                aCategory.getDeletedAt()
        );
    }
}
