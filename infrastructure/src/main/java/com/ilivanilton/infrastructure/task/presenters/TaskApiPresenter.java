package com.ilivanilton.infrastructure.task.presenters;

import com.ilivanilton.application.task.create.CreateTaskOutput;
import com.ilivanilton.application.task.retrieve.get.TaskOutput;
import com.ilivanilton.application.task.retrieve.list.TaskListOutput;
import com.ilivanilton.infrastructure.task.models.CreateTaskResponse;
import com.ilivanilton.infrastructure.task.models.TaskListResponse;
import com.ilivanilton.infrastructure.task.models.TaskResponse;

public interface TaskApiPresenter {

    static CreateTaskResponse present(final CreateTaskOutput output) {
        return new CreateTaskResponse(
                output.id()
        );
    }

    static TaskListResponse present(final TaskListOutput output) {
        return new TaskListResponse(
                output.id().getValue(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.deletedAt()
        );
    }

    static TaskResponse present(final TaskOutput output) {
        return new TaskResponse(
                output.id().getValue(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.deletedAt()
        );
    }

}
