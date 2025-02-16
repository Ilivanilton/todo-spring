package com.ilivanilton.application.task.update;

import com.ilivanilton.domain.task.TaskPriority;

public record UpdateTaskCommand(
        String id,
        String description,
        TaskPriority priority,
        boolean isActive
) {
    public static UpdateTaskCommand with(
            String anId,
            String anDescription,
            TaskPriority aPriority,
            boolean isActive
    ){
        return new UpdateTaskCommand(anId,anDescription,aPriority,isActive);
    }
}
