package com.ilivanilton.application.task.retrieve.list;

import com.ilivanilton.application.UseCase;
import com.ilivanilton.domain.pagination.Pagination;
import com.ilivanilton.domain.task.TaskSearchQuery;

public abstract class ListTaskUseCase
        extends UseCase<TaskSearchQuery, Pagination<TaskListOutput>> {
}
