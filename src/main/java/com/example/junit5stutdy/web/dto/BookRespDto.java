package com.example.junit5stutdy.web.dto;

import com.example.junit5stutdy.domain.Book;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BookRespDto {
    private Long id;
    private String title;
    private String author;

    @Builder
    public BookRespDto(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
}
