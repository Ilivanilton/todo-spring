package com.ilivanilton.application.task.update;

import com.ilivanilton.domain.task.Task;

public record UpdateTaskOutput(
        String id
) {

    public static UpdateTaskOutput from(final String anId){
        return new UpdateTaskOutput(anId);
    }

    public static UpdateTaskOutput from(final Task aTask){
        return new UpdateTaskOutput(aTask.getId().getValue());
    }
}
