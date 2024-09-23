package com.example.bookShopDemo.controller;

import com.example.bookShopDemo.BookShopDemoApplication;
import com.example.bookShopDemo.model.entity.Book;
import com.example.bookShopDemo.model.entity.ShoppingCart;
import com.example.bookShopDemo.repository.ShoppingCartRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest(classes = BookShopDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
public class ShoppingCartControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    private final String BASE_URL = "http://localhost:";

    private final String CART_API = "/api/shopping-cart";

    @BeforeAll
    static void setup(@Autowired ShoppingCartRepository shoppingCartRepository) {
        ShoppingCart shoppingCart = new ShoppingCart();

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setPrice(BigDecimal.valueOf(10));
        book.setCategory("Test Category");

        shoppingCart.addItem(book, 1);
        shoppingCartRepository.save(shoppingCart);
    }

    @Test
    void addToCart() {
        //Given
        Long cartId = 1L;
        Long bookId = 1L;
        Integer quantity = 1;

        Map<String, String> params = new HashMap<>();
        params.put("bookId", bookId.toString());
        params.put("quantity", quantity.toString());

        String baseUrl = BASE_URL + port + CART_API + "/add/{shopping-cartId}";
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("bookId", bookId)
                .queryParam("quantity", quantity)
                .buildAndExpand(cartId)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        assert response.getStatusCode().is2xxSuccessful();
    }

    @Test
    void removeItemFromCart() {
        //Given
        Long cartId = 1L;
        Long bookId = 1L;
        Integer quantity = 1;

        String baseUrl = BASE_URL + port + CART_API + "/remove/{shopping-cartId}";
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("bookId", bookId)
                .queryParam("quantity", quantity)
                .buildAndExpand(cartId)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        assert response.getStatusCode().is2xxSuccessful();
    }

    @Test
    void getItemsInCart() {
        //Given
        Long cartId = 1L;

        //When
        ResponseEntity<List> response = restTemplate.getForEntity(
                BASE_URL + port + CART_API + "/" + cartId,
                List.class);

        assert response.getStatusCode().is2xxSuccessful();
    }

    @Test
    void getTotalPrice() {
        //Given
        Long cartId = 1L;

        //When
        ResponseEntity<BigDecimal> response = restTemplate.getForEntity(
                BASE_URL + port + CART_API + "/checkout/" + cartId,
                BigDecimal.class);

        //assertEquals(BigDecimal.valueOf(10.0), response.getBody());
        assert response.getStatusCode().is2xxSuccessful();
    }


}
