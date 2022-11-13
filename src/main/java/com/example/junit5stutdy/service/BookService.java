package com.example.junit5stutdy.service;

import com.example.junit5stutdy.domain.Book;
import com.example.junit5stutdy.domain.BookRepository;
import com.example.junit5stutdy.util.MailSenderAdapter;
import com.example.junit5stutdy.web.dto.BookRespDto;
import com.example.junit5stutdy.web.dto.BookSaveReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final MailSenderAdapter mailSenderAdapter;

    //1. 책쓰기
    @Transactional(rollbackFor = RuntimeException.class)
    public BookRespDto 책등록하기(BookSaveReqDto dto){
        Book bookPS = bookRepository.save(dto.toEntity());
        return bookPS.toDto();
    }

    //2. 책목록쓰기
    public List<BookRespDto> 책목록보기(){
        return bookRepository.findAll().stream()
//                .map((bookPS)->new BookRespDto().toDto(bookPS))
                .map(Book::toDto)
                .collect(Collectors.toList());
    }

    //3. 책한건보기
    public BookRespDto 책한건보기(Long id){
        Optional<Book> bookOp = bookRepository.findById(id);
        if (bookOp.isPresent()){
            return bookOp.get().toDto();
        }else{
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    }


    //4. 책삭제
    @Transactional(rollbackFor = RuntimeException.class)
    public void 책삭제하기(Long id){
        bookRepository.deleteById(id);
    }

    //5. 책수정
    @Transactional(rollbackFor = RuntimeException.class)
    public void 책수정하기(Long id, BookSaveReqDto dto){
        Optional<Book> BookOP = bookRepository.findById(id);
        if (BookOP.isPresent()){
            Book book = BookOP.get();
            book.update(dto.getTitle(), dto.getAuthor());
        }else{
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    }
}
