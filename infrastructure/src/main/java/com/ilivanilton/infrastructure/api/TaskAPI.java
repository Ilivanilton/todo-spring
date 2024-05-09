package com.ilivanilton.infrastructure.api;


import com.ilivanilton.domain.validation.handler.Notification;
import com.ilivanilton.infrastructure.task.models.CreateTaskRequest;
import com.ilivanilton.infrastructure.task.models.CreateTaskResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "tasks")
@Tag(name = "Tasks")
public interface TaskAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new task")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created successfully",
                    content = { @Content(schema = @Schema(implementation = CreateTaskResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "A validation error was thrown",
                    content = { @Content(schema = @Schema(implementation = Notification.class))}
            ),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<?> createTask(@RequestBody CreateTaskRequest input);
}
