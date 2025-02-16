package com.ilivanilton.application.task.create;

import com.ilivanilton.domain.task.TaskPriority;

public record CreateTaskCommand(
        String description,
        TaskPriority priority,
        boolean isActive
) {

    public static CreateTaskCommand with(
            final String aDescription,
            final TaskPriority aPriority,
            final boolean isActive
    ) {
        return new CreateTaskCommand(aDescription, aPriority, isActive);
    }
}
