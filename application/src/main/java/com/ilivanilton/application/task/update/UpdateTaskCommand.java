package com.ilivanilton.application.task.update;

public record UpdateTaskCommand(
        String id,
        String description,
        boolean isActive
) {
    public static UpdateTaskCommand with(
            String anId,
            String anDescription,
            boolean isActive
    ){
        return new UpdateTaskCommand(anId,anDescription,isActive);
    }
}
