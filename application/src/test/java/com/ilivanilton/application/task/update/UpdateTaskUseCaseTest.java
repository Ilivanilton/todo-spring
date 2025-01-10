package com.ilivanilton.application.task.update;

import com.ilivanilton.domain.exceptions.NotFoundException;
import com.ilivanilton.domain.task.Task;
import com.ilivanilton.domain.task.TaskGateway;
import com.ilivanilton.domain.task.TaskID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class UpdateTaskUseCaseTest extends com.ilivanilton.application.UseCaseTest {

    @InjectMocks
    private DefaultUpdateTaskUseCase useCase;

    @Mock
    private TaskGateway taskGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(taskGateway);
    }

    // 1. Teste do caminho feliz
    // 2. Teste passando uma propriedade inválida (name)
    // 3. Teste atualizando uma categoria para inativa
    // 4. Teste simulando um erro generico vindo do gateway
    // 5. Teste atualizar categoria passando ID inválido

    @Test
    public void givenAValidCommand_whenCallsUpdateTask_shouldReturnTaskId() {
        final var aTask =
                Task.newTask("Film", true);

        final var expectedDescription = "A new TAsk";
        final var expectedIsActive = true;
        final var expectedId = aTask.getId();

        final var aCommand = UpdateTaskCommand.with(
                expectedId.getValue(),
                expectedDescription,
                expectedIsActive
        );

        when(taskGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(Task.with(aTask)));

        when(taskGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(taskGateway, times(1)).findById(eq(expectedId));

        Mockito.verify(taskGateway, times(1)).update(argThat(
                aUpdatedCategory ->
                        Objects.equals(expectedDescription, aUpdatedCategory.getDescription())
                                && Objects.equals(expectedIsActive, aUpdatedCategory.isActive())
                                && Objects.equals(expectedId, aUpdatedCategory.getId())
                                && Objects.equals(aTask.getCreatedAt(), aUpdatedCategory.getCreatedAt())
                                && aTask.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt())
                                && Objects.isNull(aUpdatedCategory.getDeletedAt())
        ));
    }

    @Test
    public void givenAInvalidDescription_whenCallsUpdateTask_thenShouldReturnDomainException() {
        final var aTask =
                Task.newTask("Film", true);

        final String expectedDescription = null;
        final var expectedIsActive = true;
        final var expectedId = aTask.getId();

        final var expectedErrorMessage = "'Descricao' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand =
                UpdateTaskCommand.with(expectedId.getValue(), expectedDescription, expectedIsActive);

        when(taskGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(Task.with(aTask)));

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(taskGateway, times(0)).update(any());
    }

    @Test
    public void givenAValidInactivateCommand_whenCallsUpdateTask_shouldReturnInactiveTaskId() {
        final var aTask =
                Task.newTask("Film", true);

        final var expectedDescription = "A nv disco";
        final var expectedIsActive = false;
        final var expectedId = aTask.getId();

        final var aCommand = UpdateTaskCommand.with(
                expectedId.getValue(),
                expectedDescription,
                expectedIsActive
        );

        when(taskGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(Task.with(aTask)));

        when(taskGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        Assertions.assertTrue(aTask.isActive());
        Assertions.assertNull(aTask.getDeletedAt());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(taskGateway, times(1)).findById(eq(expectedId));

        Mockito.verify(taskGateway, times(1)).update(argThat(
                aUpdatedCategory ->
                        Objects.equals(expectedDescription, aUpdatedCategory.getDescription())
                                && Objects.equals(expectedIsActive, aUpdatedCategory.isActive())
                                && Objects.equals(expectedId, aUpdatedCategory.getId())
                                && Objects.equals(aTask.getCreatedAt(), aUpdatedCategory.getCreatedAt())
                                && aTask.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt())
                                && Objects.nonNull(aUpdatedCategory.getDeletedAt())
        ));
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {
        final var aTask =
                Task.newTask("Film", true);

        final var expectedDescription = "A descr";
        final var expectedIsActive = true;
        final var expectedId = aTask.getId();
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "Gateway error";

        final var aCommand = UpdateTaskCommand.with(
                expectedId.getValue(),
                expectedDescription,
                expectedIsActive
        );

        when(taskGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(Task.with(aTask)));

        when(taskGateway.update(any()))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(taskGateway, times(1)).update(argThat(
                aUpdatedCategory ->
                        Objects.equals(expectedDescription, aUpdatedCategory.getDescription())
                                && Objects.equals(expectedIsActive, aUpdatedCategory.isActive())
                                && Objects.equals(expectedId, aUpdatedCategory.getId())
                                && Objects.equals(aTask.getCreatedAt(), aUpdatedCategory.getCreatedAt())
                                && aTask.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt())
                                && Objects.isNull(aUpdatedCategory.getDeletedAt())
        ));
    }

    @Test
    public void givenACommandWithInvalidID_whenCallsUpdateTask_shouldReturnNotFoundException() {
        final var expectedDescription = "A new disc";
        final var expectedIsActive = false;
        final var expectedId = "123";
        final var expectedErrorMessage = "Task with ID 123 was not found";

        final var aCommand = UpdateTaskCommand.with(
                expectedId,
                expectedDescription,
                expectedIsActive
        );

        when(taskGateway.findById(eq(TaskID.from(expectedId))))
                .thenReturn(Optional.empty());

        final var actualException =
                Assertions.assertThrows(NotFoundException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

        Mockito.verify(taskGateway, times(1)).findById(eq(TaskID.from(expectedId)));

        Mockito.verify(taskGateway, times(0)).update(any());
    }
}
