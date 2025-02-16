package com.ilivanilton.infrastructure.task.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ilivanilton.domain.task.TaskPriority;

import java.time.Instant;

public record TaskListResponse(
        @JsonProperty("id") String id,
        @JsonProperty("description") String description,
        @JsonProperty("priority") TaskPriority priority,
        @JsonProperty("is_active") Boolean active,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("deleted_at") Instant deletedAt
) {
}
