package com.ilivanilton.infrastructure.api.controllers;

import com.ilivanilton.application.task.create.CreateTaskCommand;
import com.ilivanilton.application.task.create.CreateTaskOutput;
import com.ilivanilton.application.task.create.CreateTaskUseCase;
import com.ilivanilton.application.task.delete.DeleteTaskUseCase;
import com.ilivanilton.application.task.retrieve.get.GetTaskByIdUseCase;
import com.ilivanilton.application.task.retrieve.list.ListTaskUseCase;
import com.ilivanilton.application.task.update.UpdateTaskCommand;
import com.ilivanilton.application.task.update.UpdateTaskOutput;
import com.ilivanilton.application.task.update.UpdateTaskUseCase;
import com.ilivanilton.domain.pagination.Pagination;
import com.ilivanilton.domain.pagination.SearchQuery;
import com.ilivanilton.domain.task.TaskPriority;
import com.ilivanilton.domain.validation.handler.Notification;
import com.ilivanilton.infrastructure.api.TaskAPI;
import com.ilivanilton.infrastructure.task.models.*;
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
    private final DeleteTaskUseCase deleteTaskUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;

    public TaskController(
            final CreateTaskUseCase createTaskUseCase,
            final ListTaskUseCase listTaskUseCase,
            final GetTaskByIdUseCase getTaskByIdUseCase,
            final DeleteTaskUseCase deleteTaskUseCase,
            final UpdateTaskUseCase updateTaskUseCase
    ) {
        this.createTaskUseCase = Objects.requireNonNull(createTaskUseCase);
        this.listTaskUseCase = Objects.requireNonNull(listTaskUseCase);
        this.getTaskByIdUseCase = Objects.requireNonNull(getTaskByIdUseCase);
        this.deleteTaskUseCase = Objects.requireNonNull(deleteTaskUseCase);
        this.updateTaskUseCase = Objects.requireNonNull(updateTaskUseCase);
    }

    @Override
    public ResponseEntity<?> createTask(final CreateTaskRequest input) {
        final var aCommand = CreateTaskCommand.with(
                input.description(),
                TaskPriority.valueOf(input.priority()),
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
        return listTaskUseCase.execute(new SearchQuery(page, perPage, search, sort, direction))
                .map(TaskApiPresenter::present);
    }

    @Override
    public TaskResponse getById(final String id) {
        return TaskApiPresenter.present(this.getTaskByIdUseCase.execute(id));
    }

    @Override
    public void deleteById(final String anId) {
        this.deleteTaskUseCase.execute(anId);
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateTaskRequest input) {
        final var aCommand = UpdateTaskCommand.with(
                id,
                input.description(),
                TaskPriority.valueOf(input.priority()),
                input.active() != null ? input.active() : true
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<UpdateTaskOutput, ResponseEntity<?>> onSuccess =
                ResponseEntity::ok;

        return this.updateTaskUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

}
