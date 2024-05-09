package com.ilivanilton.infrastructure.task.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ilivanilton.domain.validation.handler.Notification;

public record CreateTaskResponse(
        @JsonProperty("id") String id
) {
    public static CreateTaskResponse from(Notification notification) {
        return new CreateTaskResponse(notification.toString());
    }
}
