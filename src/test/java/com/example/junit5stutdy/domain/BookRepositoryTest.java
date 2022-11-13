package com.example.junit5stutdy.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest // DB와 관련된 메모리에 로딩
public class BookRepositoryTest {


    @Autowired
    private BookRepository bookRepository;

//    @BeforeAll //테스트 시작전에 한번만 실행
    @BeforeEach // 각 테스트 시작전에 한번씩 실행
    public void 데이터준비(){
        //given(데이터 준비)
        String title = "junit5";
        String author = "한규범";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        bookRepository.save(book);
    }
    // 가정 1: [데이터준비() + 1 책등록](T), [데이터준비() + 2 책 목록보기](T) -> 사이즈1
    // 가정 2: [데이터준비() + 1 책등록 + 데이터준비() + 2 책 목록보기}(T) -> 사이즈2


    // 1. 책 등록
    @Test
    public void 책등록_test() throws Exception{
        //given(데이터 준비)
        String title = "junit5";
        String author = "한규범";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        //when(테스트 실행)
        Book bookPS = bookRepository.save(book);

        //then(검증)
        assertEquals(title,bookPS.getTitle());
        assertEquals(author,bookPS.getAuthor());
     }//트랜잭션 종료 (저장된 데이터를 초기화함) - 트랜잭션 종료 안되게 함

    //2. 책 목록보기
    @Test
    public void 책목록보기_test() throws Exception{
        //given(데이터 준비)
        String title = "junit5";
        String author = "한규범";

        //when
        List<Book> bookPS = bookRepository.findAll();

        System.out.println("사이즈 : ==========================="+bookPS.size());

        //then
        assertEquals(title,bookPS.get(0).getTitle());
        assertEquals(author,bookPS.get(0).getAuthor());
     }

    //3. 책 한건보기
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책한건보기_test() throws Exception{
        //given
        String title = "junit5";
        String author = "한규범";

        //when
        Book bookPS = bookRepository.findById(1L).get();

        //then
        assertEquals(title,bookPS.getTitle());
        assertEquals(author,bookPS.getAuthor());

     }

    //4. 책 수정
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책수정_test() throws Exception{
        //given
        Long id = 1L;
        String title = "junit5";
        String author = "저자변경";
        Book book = new Book(id, title, author);

        //when

//        bookRepository.findAll().stream()
//                .forEach((b) -> {
//                    System.out.println(b.getId());
//                    System.out.println(b.getTitle());
//                    System.out.println(b.getAuthor());
//                    System.out.println("1. ==================");
//                });

        Book bookPS = bookRepository.save(book);

//        bookRepository.findAll().stream()
//                .forEach((b) -> {
//                    System.out.println(b.getId());
//                    System.out.println(b.getTitle());
//                    System.out.println(b.getAuthor());
//                    System.out.println("2. ==================");
//                });


        //then

        assertEquals(id, bookPS.getId());
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());

     }

    //5. 책 삭제
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책삭제_Test() throws Exception{
        //given
        Long id = 1L;

        //when
        bookRepository.deleteById(id);

        //then
        assertFalse(bookRepository.findById(id).isPresent());
    }

}
