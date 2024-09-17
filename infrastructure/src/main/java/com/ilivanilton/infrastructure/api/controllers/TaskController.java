package com.ilivanilton.infrastructure.api.controllers;

import com.ilivanilton.application.task.create.CreateTaskCommand;
import com.ilivanilton.application.task.create.CreateTaskOutput;
import com.ilivanilton.application.task.create.CreateTaskUseCase;
import com.ilivanilton.application.task.retrieve.get.GetTaskByIdUseCase;
import com.ilivanilton.application.task.retrieve.list.ListTaskUseCase;
import com.ilivanilton.domain.pagination.Pagination;
import com.ilivanilton.domain.task.TaskSearchQuery;
import com.ilivanilton.domain.validation.handler.Notification;
import com.ilivanilton.infrastructure.api.TaskAPI;
import com.ilivanilton.infrastructure.task.models.CreateTaskRequest;
import com.ilivanilton.infrastructure.task.models.CreateTaskResponse;
import com.ilivanilton.infrastructure.task.models.TaskListResponse;
import com.ilivanilton.infrastructure.task.models.TaskResponse;
import com.ilivanilton.infrastructure.task.presenters.TaskApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class TaskController implements TaskAPI {

    private final CreateTaskUseCase createTaskUseCase;
    private final ListTaskUseCase listTaskUseCase;
    private final GetTaskByIdUseCase getTaskByIdUseCase;

    public TaskController(
            final CreateTaskUseCase createTaskUseCase,
            final ListTaskUseCase listTaskUseCase,
            final GetTaskByIdUseCase getTaskByIdUseCase
    ) {
        this.createTaskUseCase = Objects.requireNonNull(createTaskUseCase);
        this.listTaskUseCase = Objects.requireNonNull(listTaskUseCase);
        this.getTaskByIdUseCase = Objects.requireNonNull(getTaskByIdUseCase);
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

    @Override
    public Pagination<TaskListResponse> listTasks(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        return listTaskUseCase.execute(new TaskSearchQuery(page, perPage, search, sort, direction))
                .map(TaskApiPresenter::present);
    }

    @Override
    public TaskResponse getById(final String id) {
        return TaskApiPresenter.present(this.getTaskByIdUseCase.execute(id));
    }

}
