package com.ilivanilton.application.task.delete;

import com.ilivanilton.domain.task.TaskGateway;
import com.ilivanilton.domain.task.TaskID;

import java.util.Objects;

public class DefaultDeleteTaskUseCase extends DeleteTaskUseCase {

    private final TaskGateway taskGateway;

    public DefaultDeleteTaskUseCase(final TaskGateway taskGateway) {
        this.taskGateway = Objects.requireNonNull(taskGateway);
    }

    @Override
    public void execute(String anIn) {
        this.taskGateway.deleteById(TaskID.from(anIn));
    }
}
