package com.ilivanilton.application.task.retrieve.get;

import com.ilivanilton.domain.exceptions.NotFoundException;
import com.ilivanilton.domain.task.Task;
import com.ilivanilton.domain.task.TaskGateway;
import com.ilivanilton.domain.task.TaskID;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetTaskByIdUseCase extends GetTaskByIdUseCase{

    private final TaskGateway taskGateway;

    public DefaultGetTaskByIdUseCase(final TaskGateway taskGateway) {
        this.taskGateway = Objects.requireNonNull(taskGateway);
    }

    public TaskOutput execute(final String anId) {
        final var aTaskID = TaskID.from(anId);
        return this.taskGateway.findById(aTaskID)
                .map(TaskOutput::from)
                .orElseThrow(notFound(aTaskID));
    }

    private Supplier<NotFoundException> notFound(final TaskID anId) {
        return () -> NotFoundException.with(Task.class, anId);
    }

}
