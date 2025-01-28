package com.ilivanilton.application.task.retrieve.list;

import com.ilivanilton.application.UseCase;
import com.ilivanilton.domain.pagination.Pagination;
import com.ilivanilton.domain.pagination.SearchQuery;

public abstract class ListTaskUseCase
        extends UseCase<SearchQuery, Pagination<TaskListOutput>> {
}
