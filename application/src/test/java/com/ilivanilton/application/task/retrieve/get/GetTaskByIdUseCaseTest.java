package com.ilivanilton.application.task.retrieve.get;

import com.ilivanilton.application.UseCaseTest;
import com.ilivanilton.domain.exceptions.NotFoundException;
import com.ilivanilton.domain.task.Task;
import com.ilivanilton.domain.task.TaskGateway;
import com.ilivanilton.domain.task.TaskID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class GetTaskByIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultGetTaskByIdUseCase useCase;

    @Mock
    private TaskGateway taskGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(taskGateway);
    }

    @Test
    public void givenAValidId_whenCallsGetTask_shouldReturnTask() {
        final var expectedDescription = "A desc";
        final var expectedIsActive = true;

        final var aTask =
                Task.newTask(expectedDescription, expectedIsActive);

        final var expectedId = aTask.getId();

        when(taskGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(aTask.clone()));

        final var actualTask = useCase.execute(expectedId.getValue());

        Assertions.assertEquals(expectedId, actualTask.id());
        Assertions.assertEquals(expectedDescription, actualTask.description());
        Assertions.assertEquals(expectedIsActive, actualTask.isActive());
        Assertions.assertEquals(aTask.getCreatedAt(), actualTask.createdAt());
        Assertions.assertEquals(aTask.getUpdatedAt(), actualTask.updatedAt());
        Assertions.assertEquals(aTask.getDeletedAt(), actualTask.deletedAt());
    }

    @Test
    public void givenAInvalidId_whenCallsGetTask_shouldReturnNotFound() {
        final var expectedErrorMessage = "Task with ID 123 was not found";
        final var expectedId = TaskID.from("123");

        when(taskGateway.findById(eq(expectedId)))
                .thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(
                NotFoundException.class,
                () -> useCase.execute(expectedId.getValue())
        );

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_shouldReturnException() {
        final var expectedErrorMessage = "Gateway error";
        final var expectedId = TaskID.from("123");

        when(taskGateway.findById(eq(expectedId)))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualException = Assertions.assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(expectedId.getValue())
        );

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }
}