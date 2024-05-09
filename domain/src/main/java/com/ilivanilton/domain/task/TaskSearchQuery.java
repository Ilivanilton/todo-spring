package com.ilivanilton.domain.task;

public record TaskSearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction
) {
}
