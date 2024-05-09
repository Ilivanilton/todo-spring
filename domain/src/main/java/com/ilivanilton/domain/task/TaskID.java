package com.ilivanilton.domain.task;

import com.ilivanilton.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class TaskID extends Identifier {

    private final String value;

    private TaskID(String value) {
        this.value = value;
    }

    public static TaskID from(String anId) {
        return new TaskID(anId);
    }

    public static TaskID from(UUID anId) {
        return new TaskID(anId.toString().toLowerCase());
    }

    public static TaskID unique(){
        return TaskID.from(UUID.randomUUID());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TaskID taskID = (TaskID) o;
        return Objects.equals(value, taskID.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
