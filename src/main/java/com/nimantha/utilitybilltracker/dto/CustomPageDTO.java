package com.nimantha.utilitybilltracker.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class CustomPageDTO<T> {
    private final int page;
    private final int size;
    private final int totalPages;
    private final long totalElements;
    private final List<T> content;

    public CustomPageDTO(Page<T> page) {
        this.page = page.getNumber();
        this.size = page.getSize();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.content = page.getContent();
    }
}
