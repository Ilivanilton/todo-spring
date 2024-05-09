package com.ilivanilton.infrastructure.task.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateTaskRequest(
        @JsonProperty("description") String description,
        @JsonProperty("is_active") Boolean active
) {
}
