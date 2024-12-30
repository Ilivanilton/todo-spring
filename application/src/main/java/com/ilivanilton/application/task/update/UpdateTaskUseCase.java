package com.ilivanilton.application.task.update;

import com.ilivanilton.application.UseCase;
import com.ilivanilton.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateTaskUseCase
    extends UseCase<UpdateTaskCommand, Either<Notification, UpdateTaskOutput>> {}
