package com.ilivanilton.application.task.create;

import com.ilivanilton.domain.task.Task;
import com.ilivanilton.domain.task.TaskGateway;
import com.ilivanilton.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultCreateTaskUseCase extends CreateTaskUseCase {

    private final TaskGateway taskGateway;

    public DefaultCreateTaskUseCase(final TaskGateway taskGateway) {
        this.taskGateway = Objects.requireNonNull(taskGateway);
    }

    @Override
    public Either<Notification, CreateTaskOutput> execute(final CreateTaskCommand aCommand) {
        final var aDescription = aCommand.description();
        final var isActive = aCommand.isActive();

        final var notification = Notification.create();

        final var aTask = Task.newTask(aDescription, isActive);
        aTask.validate(notification);

        return notification.hasError() ? Left(notification) : create(aTask);
    }

    private Either<Notification, CreateTaskOutput> create(final Task aTask) {
        return Try(() -> this.taskGateway.create(aTask))
                .toEither()
                .bimap(Notification::create, CreateTaskOutput::from);
    }
}
