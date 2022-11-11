package com.example.junit5stutdy.web.dto;

import com.example.junit5stutdy.domain.Book;
import lombok.Setter;

@Setter //Controller에서 Setter가 호출되면서 Dto도 넘어온다.
public class BookSaveReqDto {
    private String title;
    private String author;

    public Book toEntity(){
        return Book.builder()
                .title(title)
                .author(author)
                .build();
    }
}
