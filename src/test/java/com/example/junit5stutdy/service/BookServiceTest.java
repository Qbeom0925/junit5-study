package com.example.junit5stutdy.service;

import com.example.junit5stutdy.domain.Book;
import com.example.junit5stutdy.domain.BookRepository;
import com.example.junit5stutdy.util.MailSender;
import com.example.junit5stutdy.web.dto.BookRespDto;
import com.example.junit5stutdy.web.dto.BookSaveReqDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MailSender mailSender;

    @Test
    public void 책등록하기_테스트() throws Exception{
        //given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("junit5 공부");
        dto.setAuthor("한규범");

        //stub
        when(bookRepository.save(any())).thenReturn(dto.toEntity());
//        when(mailSender.send()).thenReturn(true);

        //when
        BookRespDto bookRespDto = bookService.책등록하기(dto);

        //then
        assertThat(dto.getTitle()).isEqualTo(bookRespDto.getTitle());
        assertThat(dto.getAuthor()).isEqualTo(bookRespDto.getAuthor());
     }

     @Test
    public void 책목록보기_테스트(){
        //given

        //stub(가설)
         List<Book> books = Arrays.asList(
                 new Book(1L, "junit","한규범"),
                 new Book(2L, "junit2","한규범2")
         );
         when(bookRepository.findAll()).thenReturn(books);

        //when(실행)
         List<BookRespDto> dtos = bookService.책목록보기();

         //then(검증)
         assertThat(dtos.get(0).getTitle()).isEqualTo("junit");
         assertThat(dtos.get(0).getAuthor()).isEqualTo("한규범");
         assertThat(dtos.get(1).getTitle()).isEqualTo("junit2");
         assertThat(dtos.get(1).getAuthor()).isEqualTo("한규범2");
     }

     @Test
     public void 책한거보기_테스트() throws Exception{
         //given
         Long id = 1L;
         Book book = new Book(1L, "junit5","한규범");
         Optional<Book> bookOP = Optional.of(book);
         //stub
         when(bookRepository.findById(id)).thenReturn(bookOP);

         //when
         BookRespDto bookRespDto = bookService.책한건보기(id);

         //then
         assertThat(bookRespDto.getTitle()).isEqualTo(book.getTitle());
         assertThat(bookRespDto.getAuthor()).isEqualTo(book.getAuthor());
     }

     @Test
     public void 책수정하기_테스트() throws Exception{
         //given
         Long id = 1L;
         BookSaveReqDto dto = new BookSaveReqDto();
         dto.setTitle("스프링공부");
         dto.setAuthor("한규범");

         //stub
         Book book = new Book(1L, "junit5","한규범");
         Optional<Book> bookOP = Optional.of(book);
         when(bookRepository.findById(id)).thenReturn(bookOP);

         //when
         BookRespDto bookRespDto = bookService.책수정하기(id, dto);

         //then
         assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());
         assertThat(bookRespDto.getAuthor()).isEqualTo(dto.getAuthor());
      }
}
