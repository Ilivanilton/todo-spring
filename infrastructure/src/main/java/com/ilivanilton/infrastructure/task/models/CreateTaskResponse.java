package com.ilivanilton.infrastructure.task.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateTaskResponse(
        @JsonProperty("id") String id
) {
}
