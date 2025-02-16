package com.ilivanilton.application.task.update;

import com.ilivanilton.domain.exceptions.DomainException;
import com.ilivanilton.domain.exceptions.NotFoundException;
import com.ilivanilton.domain.task.Task;
import com.ilivanilton.domain.task.TaskGateway;
import com.ilivanilton.domain.task.TaskID;
import com.ilivanilton.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.function.Supplier;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultUpdateTaskUseCase extends UpdateTaskUseCase{

    private final TaskGateway taskGateway;

    public DefaultUpdateTaskUseCase(final TaskGateway taskGateway) {
        this.taskGateway = Objects.requireNonNull(taskGateway);
    }

    @Override
    public Either<Notification, UpdateTaskOutput> execute(UpdateTaskCommand aCommand) {
        final var anId = TaskID.from(aCommand.id());
        final var aDescription = aCommand.description();
        final var aPriority = aCommand.priority();
        final var isActive = aCommand.isActive();

        final var aTask = this.taskGateway.findById(anId).orElseThrow(notFound(anId));

        final var notification = Notification.create();

        aTask.update(aDescription, aPriority, isActive).validate(notification);

        return notification.hasError() ? Left(notification) : update(aTask);
    }

    private Either<Notification, UpdateTaskOutput> update(final Task aCategory) {
        return Try(() -> this.taskGateway.update(aCategory))
                .toEither()
                .bimap(Notification::create, UpdateTaskOutput::from);
    }

    private Supplier<DomainException> notFound(final TaskID anId) {
        return () -> NotFoundException.with(Task.class, anId);
    }
}
