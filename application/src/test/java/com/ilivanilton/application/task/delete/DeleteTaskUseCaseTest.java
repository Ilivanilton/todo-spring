package com.ilivanilton.application.task.delete;

import com.ilivanilton.domain.task.Task;
import com.ilivanilton.domain.task.TaskGateway;
import com.ilivanilton.domain.task.TaskID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.ilivanilton.application.UseCaseTest;

public class DeleteTaskUseCaseTest extends UseCaseTest {


    @InjectMocks
    private DefaultDeleteTaskUseCase useCase;

    @Mock
    private TaskGateway taskGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(taskGateway);
    }

    @Test
    public void givenAValidId_whenCallsDeleteTask_shouldBeOK() {
        final var aTask = Task.newTask("descr", true);
        final var expectedId = aTask.getId();

        doNothing()
                .when(taskGateway).deleteById(eq(expectedId));

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        Mockito.verify(taskGateway, times(1)).deleteById(eq(expectedId));
    }

    @Test
    public void givenAInvalidId_whenCallsDeleteTask_shouldBeOK() {
        final var expectedId = TaskID.from("123");

        doNothing()
                .when(taskGateway).deleteById(eq(expectedId));

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        Mockito.verify(taskGateway, times(1)).deleteById(eq(expectedId));
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_shouldReturnException() {
        final var aTask = Task.newTask("Desc", true);
        final var expectedId = aTask.getId();

        doThrow(new IllegalStateException("Gateway error"))
                .when(taskGateway).deleteById(eq(expectedId));

        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        Mockito.verify(taskGateway, times(1)).deleteById(eq(expectedId));
    }
}