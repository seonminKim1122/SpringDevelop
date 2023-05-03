package com.example.springdevelop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@NoArgsConstructor
@Setter
public class PageRequestDto {
    private int page;
    private int size = 10;
    private String sortingCriteria;
    private boolean asc;

    public Pageable createPageable() {
        if (asc) {
            return PageRequest.of(page-1, size, Sort.by(sortingCriteria).ascending());
        } else {
            return PageRequest.of(page-1, size, Sort.by(sortingCriteria).descending());
        }
    }
}
