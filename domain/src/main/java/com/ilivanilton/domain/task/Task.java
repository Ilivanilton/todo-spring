package com.ilivanilton.domain.task;

import com.ilivanilton.domain.AggregateRoot;
import com.ilivanilton.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.Objects;

public class Task extends AggregateRoot<TaskID> {
    private String description;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Task(
            final TaskID anId,
            final String aDescription,
            final boolean isActive,
            final Instant aCreationDate,
            final Instant aUpdateDate,
            final Instant aDeletedDate
    ){
        super(anId);
        this.description = aDescription;
        this.active = isActive;
        this.createdAt = Objects.requireNonNull(aCreationDate, "'createdAt' should not be null");
        this.updatedAt = Objects.requireNonNull(aUpdateDate, "'updatedAt' should not be null");
        this.deletedAt = aDeletedDate;
    }

    public static Task newTask(
            final String aDesctiption,
            final boolean isActive
    ){
        final Instant now = Instant.now();
        return new Task(
                TaskID.unique(),
                aDesctiption,
                isActive,
                now,
                now,
                isActive ? null : now
        );
    }

    public static Task with(
            final TaskID anId,
            final String aDescription,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ){
        return new Task( anId, aDescription,
                active, createdAt,
                updatedAt, deletedAt);
    }

    public static Task with(final Task aTask){
        return with(
                aTask.getId(),
                aTask.getDescription(),
                aTask.isActive(),
                aTask.getCreatedAt(),
                aTask.getUpdatedAt(),
                aTask.getDeletedAt()
        );
    }

    public Task activate(){
        this.deletedAt = null;
        this.updatedAt =Instant.now();
        this.active = true;
        return this;
    }

    public Task deactivate(){
        if(getDeletedAt() == null)
            this.deletedAt = Instant.now();
        this.active = false;
        this.updatedAt = Instant.now();
        return this;
    }

    public Task update(
            final String aDescription,
            final boolean isActive
    ){
        if (isActive){
            activate();
        }else{
            deactivate();
        }
        this.description = aDescription;
        this.updatedAt = Instant.now();
        return this;
    }

    @Override
    public void validate(ValidationHandler handler) {
        new TaskValidator(this, handler).validate();
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }
}
