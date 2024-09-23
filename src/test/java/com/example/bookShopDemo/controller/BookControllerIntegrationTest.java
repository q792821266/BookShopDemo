package com.example.bookShopDemo.controller;

import com.example.bookShopDemo.BookShopDemoApplication;
import com.example.bookShopDemo.model.dto.BookDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = BookShopDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
public class BookControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URL = "http://localhost:";

    private final String BOOK_API = "/api/books";

    @BeforeAll
    static void setup() {

    }

    @Test
    public void createBook() {
        // Test the creation of a book
        BookDTO book = new BookDTO(1L, "Test Book", "Test Author", BigDecimal.valueOf(10.00), "Test Category");
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(BASE_URL + port + BOOK_API, book, String.class);
        assertEquals(201, responseEntity.getStatusCode().value());
    }

    //@Sql({"classpath:schema.sql", "classpath:data.sql"})
    @Test
    public void getAllBooks() {
        assertEquals(6, this.restTemplate
                .getForObject(BASE_URL + port + BOOK_API, List.class)
                .size());
    }
}
