package com.ilivanilton.infrastructure.task.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ilivanilton.domain.task.TaskPriority;

public record CreateTaskRequest(
        @JsonProperty("description") String description,
        @JsonProperty("priority") TaskPriority priority,
        @JsonProperty("is_active") Boolean active
) {
}
