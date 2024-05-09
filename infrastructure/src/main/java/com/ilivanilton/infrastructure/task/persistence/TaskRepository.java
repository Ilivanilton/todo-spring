package com.ilivanilton.infrastructure.task.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskJpaEntity, String> {

    Page<TaskJpaEntity> findAll(Specification<TaskJpaEntity> whereClause, Pageable page);
}
