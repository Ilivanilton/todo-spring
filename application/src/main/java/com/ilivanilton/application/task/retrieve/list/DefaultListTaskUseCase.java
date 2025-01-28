package com.ilivanilton.application.task.retrieve.list;

import com.ilivanilton.domain.pagination.Pagination;
import com.ilivanilton.domain.pagination.SearchQuery;
import com.ilivanilton.domain.task.TaskGateway;

import java.util.Objects;

public class DefaultListTaskUseCase extends ListTaskUseCase {

    private final TaskGateway taskGateway;

    public DefaultListTaskUseCase(final TaskGateway taskGateway) {
        this.taskGateway = Objects.requireNonNull(taskGateway);
    }

    @Override
    public Pagination<TaskListOutput> execute(final SearchQuery aQuery) {
        return this.taskGateway.findAll(aQuery).map(TaskListOutput::from);
    }
}
