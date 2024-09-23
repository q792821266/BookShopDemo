package com.example.bookShopDemo.controller;

import com.example.bookShopDemo.model.dto.BookDTO;
import com.example.bookShopDemo.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/books")
public class BookController {

        private final BookService bookService;

        public BookController(BookService bookService) {
            this.bookService = bookService;
        }

        @PostMapping
        public ResponseEntity<BookDTO> createBook(@RequestBody @Validated BookDTO bookDTO) throws URISyntaxException {
            BookDTO newBook =  bookService.createBook(bookDTO);
            return ResponseEntity.created(new URI("/api/books/" + newBook.getId())).body(newBook);
        }

        @GetMapping
        public ResponseEntity<List<BookDTO>> getAllBooks() {
            log.info("Request to get all Books");
            return ResponseEntity.ok(bookService.getAllBooks());
        }
}
