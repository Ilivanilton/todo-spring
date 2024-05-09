package com.ilivanilton.application.task.create;

import com.ilivanilton.domain.task.Task;

public record CreateTaskOutput(
        String id
) {

    public static CreateTaskOutput from(final String anId) {
        return new CreateTaskOutput(anId);
    }

    public static CreateTaskOutput from(final Task aTask) {
        return new CreateTaskOutput(aTask.getId().getValue());
    }
}
