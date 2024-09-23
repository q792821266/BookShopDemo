package com.example.bookShopDemo.service;

import com.example.bookShopDemo.model.dto.BookDTO;

import java.util.List;

public interface BookService {

    BookDTO createBook(BookDTO book);

    List<BookDTO> getAllBooks();
}
