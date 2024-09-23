package com.example.bookShopDemo.service.impl;

import java.util.*;

import com.example.bookShopDemo.mapper.BookMapper;
import com.example.bookShopDemo.model.dto.BookDTO;
import com.example.bookShopDemo.model.entity.Book;
import com.example.bookShopDemo.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createBook_ValidBookDTO_ShouldMapToBookAndSave() {
        // Arrange
        BookDTO bookDTO = new BookDTO(1L, "Test Book", "John Doe", new BigDecimal("10.99"), "Fiction");
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("John Doe");
        book.setPrice(new BigDecimal("10.99"));
        book.setCategory("Fiction");

        when(bookMapper.bookDTOToBook(bookDTO)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);

        // Act
        BookDTO createdBookDTO = bookService.createBook(bookDTO);

        // Assert
        assertEquals(bookDTO, createdBookDTO);
        verify(bookMapper, times(1)).bookDTOToBook(bookDTO);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void getAllBooks_BooksExist_ReturnsBookDTOList() {
        // Arrange
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book1");
        book1.setAuthor("Author1");
        book1.setPrice(new BigDecimal("10.99"));
        book1.setCategory("Category1");

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book2");
        book2.setAuthor("Author2");
        book2.setPrice(new BigDecimal("19.99"));
        book2.setCategory("Category2");

        List<Book> books = Arrays.asList(book1, book2);

        BookDTO bookDTO1 = new BookDTO(1L, "Book1", "Author1", new BigDecimal("10.99"), "Category1");
        BookDTO bookDTO2 = new BookDTO(2L, "Book2", "Author2", new BigDecimal("19.99"), "Category2");

        List<BookDTO> bookDTOs = Arrays.asList(bookDTO1, bookDTO2);

        when(bookRepository.findAll()).thenReturn(books);
        when(bookMapper.booksToBookDTOs(books)).thenReturn(bookDTOs);

        // Act
        List<BookDTO> result = bookService.getAllBooks();

        // Assert
        assertEquals(2, result.size());
        assertEquals(bookDTO1, result.get(0));
        assertEquals(bookDTO2, result.get(1));
    }

    @Test
    public void getAllBooks_NoBooksExist_ReturnsEmptyList() {
        // Arrange
        List<Book> books = new ArrayList<>();

        when(bookRepository.findAll()).thenReturn(books);
        when(bookMapper.booksToBookDTOs(books)).thenReturn(new ArrayList<BookDTO>());

        // Act
        List<BookDTO> result = bookService.getAllBooks();

        // Assert
        assertEquals(0, result.size());
    }
}
