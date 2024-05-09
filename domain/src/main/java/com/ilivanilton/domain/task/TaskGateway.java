package com.ilivanilton.domain.task;

import com.ilivanilton.domain.pagination.Pagination;

import java.util.Optional;

public interface TaskGateway {

    Task create(Task aTask);

    void deleteById(TaskID anId);

    Optional<Task> findById(TaskID anId);

    Task update(Task aTask);

    Pagination<Task> findAll(TaskSearchQuery aQuery);
}
