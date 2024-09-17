package com.ilivanilton.infrastructure.api;


import com.ilivanilton.domain.pagination.Pagination;
import com.ilivanilton.domain.validation.handler.Notification;
import com.ilivanilton.infrastructure.task.models.CreateTaskRequest;
import com.ilivanilton.infrastructure.task.models.CreateTaskResponse;
import com.ilivanilton.infrastructure.task.models.TaskListResponse;
import com.ilivanilton.infrastructure.task.models.TaskResponse;
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

    @GetMapping
    @Operation(summary = "List all tasks paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listed successfully"),
            @ApiResponse(responseCode = "422", description = "A invalid parameter was received"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    Pagination<TaskListResponse> listTasks(
            @RequestParam(name = "search", required = false, defaultValue = "") final String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "description") final String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") final String direction
    );

    @GetMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get a task by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Task was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    TaskResponse getById(@PathVariable(name = "id") String id);

}
