package com.ilivanilton.application.task.create;

import com.ilivanilton.application.UseCaseTest;
import com.ilivanilton.domain.task.TaskGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class CreateTaskUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateTaskUseCase useCase;

    @Mock
    private TaskGateway taskGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(taskGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsCreateTask_shouldReturnTaskId() {
        final var expectedDescription = "A new Task";
        final var expectedIsActive = true;

        final var aCommand = CreateTaskCommand.with(expectedDescription, expectedIsActive);

        when(taskGateway.create(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(taskGateway, times(1)).create(argThat(aTask ->
                Objects.equals(expectedDescription, aTask.getDescription())
                        && Objects.equals(expectedIsActive, aTask.isActive())
                        && Objects.nonNull(aTask.getId())
                        && Objects.nonNull(aTask.getCreatedAt())
                        && Objects.nonNull(aTask.getUpdatedAt())
                        && Objects.isNull(aTask.getDeletedAt())
        ));
    }

    @Test
    public void givenAInvalidDescription_whenCallsCreateTask_thenShouldReturnDomainException() {
        final String expectedDescription = null;
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'Descricao' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand =
                CreateTaskCommand.with(expectedDescription, expectedIsActive);

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(taskGateway, times(0)).create(any());
    }

    @Test
    public void givenAValidCommandWithInactiveTask_whenCallsCreateTask_shouldReturnInactiveCategoryId() {
        final var expectedDescription = "A new Description";
        final var expectedIsActive = false;

        final var aCommand =
                CreateTaskCommand.with(expectedDescription, expectedIsActive);

        when(taskGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(taskGateway, times(1)).create(argThat(aTask ->
                Objects.equals(expectedDescription, aTask.getDescription())
                        && Objects.equals(expectedIsActive, aTask.isActive())
                        && Objects.nonNull(aTask.getId())
                        && Objects.nonNull(aTask.getCreatedAt())
                        && Objects.nonNull(aTask.getUpdatedAt())
                        && Objects.nonNull(aTask.getDeletedAt())
        ));
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {
        final var expectedDescription = "A new task";
        final var expectedIsActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "Gateway error";

        final var aCommand =
                CreateTaskCommand.with(expectedDescription, expectedIsActive);

        when(taskGateway.create(any()))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(taskGateway, times(1)).create(argThat(aCategory ->
                Objects.equals(expectedDescription, aCategory.getDescription())
                        && Objects.equals(expectedIsActive, aCategory.isActive())
                        && Objects.nonNull(aCategory.getId())
                        && Objects.nonNull(aCategory.getCreatedAt())
                        && Objects.nonNull(aCategory.getUpdatedAt())
                        && Objects.isNull(aCategory.getDeletedAt())
        ));
    }
}