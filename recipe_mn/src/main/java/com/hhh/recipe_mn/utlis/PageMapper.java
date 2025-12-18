package com.hhh.recipe_mn.utlis;

import com.hhh.recipe_mn.dto.response.PageResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public class PageMapper {

    public static <E, D> PageResponse<List<D>> toPageResponse(
            Page<E> page,
            Function<E, D> mapper
    ) {
        return new PageResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getContent()
                        .stream()
                        .map(mapper)
                        .toList()
        );
    }
}
