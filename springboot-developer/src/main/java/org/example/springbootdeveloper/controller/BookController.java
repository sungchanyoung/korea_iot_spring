package org.example.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbootdeveloper.dto.request.BookRequestDTO;
import org.example.springbootdeveloper.dto.request.BookRequestUpdateDTO;
import org.example.springbootdeveloper.dto.response.BookResponseDTO;
import org.example.springbootdeveloper.dto.response.ResponseDto;
import org.example.springbootdeveloper.entity.Category;
import org.example.springbootdeveloper.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
//초기화  되지 않은 final 필드나 @NonNull이 붙은 필드에 대해 생성자를 생성
public class BookController {
    // Service 객체를 주입 받아 사용하는 변수
    private final BookService bookService;

    // 생성자 주입 - RequiredArgsConstructor로 대체
//    public BookController(BookService bookService) {
//        this.bookService = bookService;
//    }

    // 책 생성
    @PostMapping
    public ResponseEntity<ResponseDto<BookResponseDTO>> createBook(@RequestBody BookRequestDTO requestDto) {
        ResponseDto<BookResponseDTO> result = bookService.createBook(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 전체 책 조회
    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        List<BookResponseDTO> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    // 단건 책 조회
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        BookResponseDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    // 제목에 특정 단어가 포함된 책 조회
    @GetMapping("/search/title")
    public ResponseEntity<List<BookResponseDTO>> getBooksByTitleContaining(
            @RequestParam String keyword
    ) {
        List<BookResponseDTO> books = bookService.getBooksByTitleContaining(keyword);
        return ResponseEntity.ok(books);
    }

    // 카테고리별 책 조회
    @GetMapping("/category/{category}")
    public ResponseEntity<List<BookResponseDTO>> getBooksByCategory(@PathVariable Category category) {
        List<BookResponseDTO> books = bookService.getBooksByCategory(category);
        return ResponseEntity.ok(books);
    }

    // 카테고리와 작성자별 책 조회
    @GetMapping("/search/category-writer")
    public ResponseEntity<List<BookResponseDTO>> getBooksByCategoryAndWriter(
            @RequestParam(required = false) Category category,
            @RequestParam String writer
    ) {
        List<BookResponseDTO> books = bookService.getBooksByCategoryAndWriter(category, writer);
        return ResponseEntity.ok(books);
    }

    // 특정 id 책 수정
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(
            @PathVariable Long id, @RequestBody BookRequestUpdateDTO requestDto
    ) {
        BookResponseDTO updatedBook = bookService.updateBook(id, requestDto);
        return ResponseEntity.ok(updatedBook);
    }

    // 특정 id 책 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
