package com.ilivanilton.domain.task;

import com.ilivanilton.domain.validation.Error;
import com.ilivanilton.domain.validation.ValidationHandler;
import com.ilivanilton.domain.validation.Validator;

public class TaskValidator extends Validator {
    public static final int NAME_MAX_LENGTH = 100;
    public static final int NAME_MIN_LENGTH = 3;
    private final Task task;

    public TaskValidator(final Task aTask, final ValidationHandler aHandler) {
        super(aHandler);
        this.task = aTask;
    }

    @Override
    public void validate() {
        checkDescriptionConstraints();
    }

    private void checkDescriptionConstraints() {
        final var description = this.task.getDescription();
        if (description == null) {
            this.validationHandler().append(new Error("'Descricao' should not be null"));
            return;
        }

        if (description.isBlank()) {
            this.validationHandler().append(new Error("'Descricao' should not be empty"));
            return;
        }

        final int length = description.trim().length();
        if (length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            this.validationHandler().append(new Error("'Descricao' must be between 3 and 100 characters"));
        }
    }
}
