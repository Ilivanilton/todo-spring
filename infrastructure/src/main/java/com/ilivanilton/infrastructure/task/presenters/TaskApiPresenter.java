package com.ilivanilton.infrastructure.task.presenters;

import com.ilivanilton.application.task.create.CreateTaskOutput;
import com.ilivanilton.infrastructure.task.models.CreateTaskResponse;

public interface TaskApiPresenter {

    static CreateTaskResponse present(final CreateTaskOutput output) {
        return new CreateTaskResponse(
                output.id()
        );
    }
}
