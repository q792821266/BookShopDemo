package com.example.bookShopDemo.service.impl;

import com.example.bookShopDemo.mapper.BookMapper;
import com.example.bookShopDemo.model.entity.Book;
import com.example.bookShopDemo.model.dto.BookDTO;
import com.example.bookShopDemo.repository.BookRepository;
import com.example.bookShopDemo.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }


    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = bookMapper.bookDTOToBook(bookDTO);
        bookRepository.save(book);
        return bookDTO;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        return bookMapper.booksToBookDTOs(bookList);
    }
}
