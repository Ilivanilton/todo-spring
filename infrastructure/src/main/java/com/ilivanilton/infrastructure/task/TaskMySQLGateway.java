package com.ilivanilton.infrastructure.task;


import com.ilivanilton.domain.pagination.Pagination;
import com.ilivanilton.domain.task.Task;
import com.ilivanilton.domain.task.TaskGateway;
import com.ilivanilton.domain.task.TaskID;
import com.ilivanilton.domain.task.TaskSearchQuery;
import com.ilivanilton.infrastructure.task.persistence.TaskJpaEntity;
import com.ilivanilton.infrastructure.task.persistence.TaskRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.ilivanilton.infrastructure.utils.SpecificationUtils.like;

@Service
public class TaskMySQLGateway implements TaskGateway {

    private final TaskRepository repository;

    public TaskMySQLGateway(final TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Task create(final Task aTask) {
        return save(aTask);
    }

    @Override
    public void deleteById(final TaskID anId) {
        final String anIdValue = anId.getValue();
        if (this.repository.existsById(anIdValue)) {
            this.repository.deleteById(anIdValue);
        }
    }

    @Override
    public Optional<Task> findById(final TaskID anId) {
        return this.repository.findById(anId.getValue())
                .map(TaskJpaEntity::toAggregate);
    }

    @Override
    public Task update(final Task aTask) {
        return save(aTask);
    }

    @Override
    public Pagination<Task> findAll(final TaskSearchQuery aQuery) {
        // Paginação
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        // Busca dinamica pelo criterio terms (name ou description)
        final var specifications = Optional.ofNullable(aQuery.terms())
                .filter(str -> !str.isBlank())
                .map(str -> {
                    final Specification<TaskJpaEntity> nameLike = like("name", str);
                    final Specification<TaskJpaEntity> descriptionLike = like("description", str);
                    return nameLike.or(descriptionLike);
                })
                .orElse(null);

        final var pageResult =
                this.repository.findAll(Specification.where(specifications), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(TaskJpaEntity::toAggregate).toList()
        );
    }

    private Task save(final Task aTask) {
        return this.repository.save(TaskJpaEntity.from(aTask)).toAggregate();
    }
}
