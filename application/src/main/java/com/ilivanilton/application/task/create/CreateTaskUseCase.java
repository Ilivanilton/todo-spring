package com.ilivanilton.application.task.create;

import com.ilivanilton.application.UseCase;
import com.ilivanilton.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateTaskUseCase
        extends UseCase<CreateTaskCommand, Either<Notification, CreateTaskOutput>> {
}
