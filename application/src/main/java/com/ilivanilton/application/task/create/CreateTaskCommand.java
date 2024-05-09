package com.ilivanilton.application.task.create;

public record CreateTaskCommand(
        String description,
        boolean isActive
) {

    public static CreateTaskCommand with(
            final String aDescription,
            final boolean isActive
    ) {
        return new CreateTaskCommand(aDescription, isActive);
    }
}
