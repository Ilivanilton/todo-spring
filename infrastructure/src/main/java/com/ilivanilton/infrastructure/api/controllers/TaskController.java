package com.ilivanilton.infrastructure.api.controllers;

import com.ilivanilton.application.task.create.CreateTaskCommand;
import com.ilivanilton.application.task.create.CreateTaskOutput;
import com.ilivanilton.application.task.create.CreateTaskUseCase;
import com.ilivanilton.domain.validation.handler.Notification;
import com.ilivanilton.infrastructure.api.TaskAPI;
import com.ilivanilton.infrastructure.task.models.CreateTaskRequest;
import com.ilivanilton.infrastructure.task.models.CreateTaskResponse;
import com.ilivanilton.infrastructure.task.presenters.TaskApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class TaskController implements TaskAPI {

    private final CreateTaskUseCase createTaskUseCase;

    public TaskController(
            final CreateTaskUseCase createTaskUseCase
    ) {
        this.createTaskUseCase = Objects.requireNonNull(createTaskUseCase);
    }

    @Override
    public ResponseEntity<?> createTask(final CreateTaskRequest input) {
        final var aCommand = CreateTaskCommand.with(
                input.description(),
                input.active() != null ? input.active() : true
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<CreateTaskOutput, ResponseEntity<CreateTaskResponse>> onSuccess = output ->
                ResponseEntity.created(URI.create("/tasks/" + output.id()))
                        .body(TaskApiPresenter.present(output));

        return this.createTaskUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

}
