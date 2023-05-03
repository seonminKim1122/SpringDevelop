package com.example.springdevelop.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PageResponseDto<E> {
    private int page;
    private int size;
    private int total;

    // 시작 페이지, 끝 페이지
    private int start;
    private int end;

    // 이전 페이지, 다음 페이지 존재 여부
    private boolean prev;
    private boolean next;

    // 해당 페이지에서 반환될 dto리스트(ex : 게시글이나 댓글)
    private List<E> dtoList;

    public PageResponseDto(PageRequestDto pageRequestDto, List<E> dtoList, int total) {
        this.page = pageRequestDto.getPage();
        this.size = pageRequestDto.getSize();

        this.total = total;
        this.dtoList = dtoList;

        // 페이지는 1~10, 11~20 처럼 10 단위로 한 화면에
        this.end = (int)Math.ceil(this.page/10.0)*10;
        this.start = this.end - 9;

        int last = (int)(Math.ceil((total/(double)size))); // 최종 페이지의 번호(total = 175, size=10 => 18 page)

        this.end = end > last ? last : end;

        this.prev = this.start > 1;

        this.next = total > this.end * this.size;
    }
}
