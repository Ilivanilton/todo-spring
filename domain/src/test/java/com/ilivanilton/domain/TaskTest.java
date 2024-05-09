package com.ilivanilton.domain;

import com.ilivanilton.domain.exceptions.DomainException;
import com.ilivanilton.domain.task.Task;
import com.ilivanilton.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TaskTest {

    @Test
    public void givenAValidParams_whenCallNewTask_thenInstantiateATask() {
        final var expectedDescription = "Uma Task";
        final var expectedIsActive = true;

        final var actualTask =
                Task.newTask(expectedDescription, expectedIsActive);

        Assertions.assertNotNull(actualTask);
        Assertions.assertNotNull(actualTask.getId());
        Assertions.assertEquals(expectedDescription, actualTask.getDescription());
        Assertions.assertEquals(expectedIsActive, actualTask.isActive());
        Assertions.assertNotNull(actualTask.getCreatedAt());
        Assertions.assertNotNull(actualTask.getUpdatedAt());
        Assertions.assertNull(actualTask.getDeletedAt());
    }

    @Test
    public void givenAnInvalidNullDescription_whenCallNewTaskAndValidate_thenShouldReceiveError() {
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'Descricao' should not be null";
        final String expectedDescription = null;
        final var expectedIsActive = true;

        final var actualTask =
                Task.newTask(expectedDescription, expectedIsActive);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualTask.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidEmptyDescription_whenCallNewTaskAndValidate_thenShouldReceiveError() {
        final var expectedDescription = "  ";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'Descricao' should not be empty";
        final var expectedIsActive = true;

        final var actualTask =
                Task.newTask(expectedDescription, expectedIsActive);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualTask.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidDescriptionLengthLessThan3_whenCallNewTaskAndValidate_thenShouldReceiveError() {
        final var expectedDescription = "Fi ";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'Descricao' must be between 3 and 100 characters";
        final var expectedIsActive = true;

        final var actualTask =
                Task.newTask(expectedDescription, expectedIsActive);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualTask.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidDescriptionLengthMoreThan100_whenCallNewTaskAndValidate_thenShouldReceiveError() {
        final var expectedDescription = """
                Gostaria de enfatizar que o consenso sobre a necessidade de qualificação auxilia a preparação e a
                composição das posturas dos órgãos dirigentes com relação às suas atribuições.
                Do mesmo modo, a estrutura atual da organização apresenta tendências no sentido de aprovar a
                manutenção das novas proposições.
                """;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'Descricao' must be between 3 and 100 characters";
        final var expectedIsActive = true;

        final var actualTask = Task.newTask(expectedDescription, expectedIsActive);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualTask.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAValidFalseIsActive_whenCallNewTaskAndValidate_thenShouldReceiveOK() {
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var actualTask = Task.newTask(expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> actualTask.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(actualTask);
        Assertions.assertNotNull(actualTask.getId());
        Assertions.assertEquals(expectedDescription, actualTask.getDescription());
        Assertions.assertEquals(expectedIsActive, actualTask.isActive());
        Assertions.assertNotNull(actualTask.getCreatedAt());
        Assertions.assertNotNull(actualTask.getUpdatedAt());
        Assertions.assertNotNull(actualTask.getDeletedAt());
    }

    @Test
    public void givenAValidActiveTask_whenCallDeactivate_thenReturnTaskInactivated() {
        final var expectedDescription = "Task";
        final var expectedIsActive = false;

        final var aTask = Task.newTask(expectedDescription, true);

        Assertions.assertDoesNotThrow(() -> aTask.validate(new ThrowsValidationHandler()));

        final var createdAt = aTask.getCreatedAt();
        final var updatedAt = aTask.getUpdatedAt();

        Assertions.assertTrue(aTask.isActive());
        Assertions.assertNull(aTask.getDeletedAt());

        final var actualTask = aTask.deactivate();

        Assertions.assertDoesNotThrow(() -> actualTask.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aTask.getId(), actualTask.getId());
        Assertions.assertEquals(expectedDescription, actualTask.getDescription());
        Assertions.assertEquals(expectedIsActive, actualTask.isActive());
        Assertions.assertEquals(createdAt, actualTask.getCreatedAt());
        Assertions.assertTrue(actualTask.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(actualTask.getDeletedAt());
    }

    @Test
    public void givenAValidInactiveTask_whenCallActivate_thenReturnTaskActivated() {
        final var expectedDescription = "Task";
        final var expectedIsActive = true;

        final var aTask = Task.newTask(expectedDescription, false);

        Assertions.assertDoesNotThrow(() -> aTask.validate(new ThrowsValidationHandler()));

        final var createdAt = aTask.getCreatedAt();
        final var updatedAt = aTask.getUpdatedAt();

        Assertions.assertFalse(aTask.isActive());
        Assertions.assertNotNull(aTask.getDeletedAt());

        final var actualTask = aTask.activate();

        Assertions.assertDoesNotThrow(() -> actualTask.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aTask.getId(), actualTask.getId());
        Assertions.assertEquals(expectedDescription, actualTask.getDescription());
        Assertions.assertEquals(expectedIsActive, actualTask.isActive());
        Assertions.assertEquals(createdAt, actualTask.getCreatedAt());
        Assertions.assertTrue(actualTask.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(actualTask.getDeletedAt());
    }

    @Test
    public void givenAValidTask_whenCallUpdate_thenReturnTaskUpdated() {
        final var expectedDescription = "Task";
        final var expectedIsActive = true;

        final var aTask = Task.newTask("A Task", expectedIsActive);

        Assertions.assertDoesNotThrow(() -> aTask.validate(new ThrowsValidationHandler()));

        final var createdAt = aTask.getCreatedAt();
        final var updatedAt = aTask.getUpdatedAt();

        final var actualTask = aTask.update(expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> actualTask.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aTask.getId(), actualTask.getId());
        Assertions.assertEquals(expectedDescription, actualTask.getDescription());
        Assertions.assertEquals(expectedIsActive, actualTask.isActive());
        Assertions.assertEquals(createdAt, actualTask.getCreatedAt());
        Assertions.assertTrue(actualTask.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(actualTask.getDeletedAt());
    }

    @Test
    public void givenAValidTask_whenCallUpdateToInactive_thenReturnTaskUpdated() {
        final var expectedDescription = "Task";
        final var expectedIsActive = false;

        final var aTask = Task.newTask( "A Task", true);

        Assertions.assertDoesNotThrow(() -> aTask.validate(new ThrowsValidationHandler()));
        Assertions.assertTrue(aTask.isActive());
        Assertions.assertNull(aTask.getDeletedAt());

        final var createdAt = aTask.getCreatedAt();
        final var updatedAt = aTask.getUpdatedAt();

        final var actualTask = aTask.update(expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> actualTask.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aTask.getId(), actualTask.getId());
        Assertions.assertEquals(expectedDescription, actualTask.getDescription());
        Assertions.assertFalse(aTask.isActive());
        Assertions.assertEquals(createdAt, actualTask.getCreatedAt());
        Assertions.assertTrue(actualTask.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(aTask.getDeletedAt());
    }

    @Test
    public void givenAValidTask_whenCallUpdateWithInvalidParams_thenReturnTaskUpdated() {
        final String expectedDescription = null;
        final var expectedIsActive = true;

        final var aTask = Task.newTask("A Task", expectedIsActive);

        Assertions.assertDoesNotThrow(() -> aTask.validate(new ThrowsValidationHandler()));

        final var createdAt = aTask.getCreatedAt();
        final var updatedAt = aTask.getUpdatedAt();

        final var actualTask = aTask.update(expectedDescription, expectedIsActive);

        Assertions.assertEquals(aTask.getId(), actualTask.getId());
        Assertions.assertEquals(expectedDescription, actualTask.getDescription());
        Assertions.assertTrue(aTask.isActive());
        Assertions.assertEquals(createdAt, actualTask.getCreatedAt());
        Assertions.assertTrue(actualTask.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(aTask.getDeletedAt());
    }
}
