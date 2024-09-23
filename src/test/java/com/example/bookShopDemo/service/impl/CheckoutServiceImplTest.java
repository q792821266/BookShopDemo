package com.example.bookShopDemo.service.impl;

import java.util.*;
import java.math.*;

import com.example.bookShopDemo.model.entity.Book;
import com.example.bookShopDemo.model.entity.ShoppingCart;
import com.example.bookShopDemo.model.entity.ShoppingCartItem;
import com.example.bookShopDemo.repository.ShoppingCartRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CheckoutServiceImplTest {

    @Mock
    private ShoppingCartRepository cartRepository;

    @InjectMocks
    private CheckoutServiceImpl checkoutService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getCartTotalPrice_CartExists_ReturnsCorrectPrice() {
        // Arrange
        Long cartId = 1L;
        ShoppingCart cart = new ShoppingCart();
        cart.setId(cartId);

        Book book = new Book();
        book.setId(1L);
        book.setPrice(new BigDecimal("10.00"));

        ShoppingCartItem item = new ShoppingCartItem();
        item.setBook(book);
        item.setQuantity(2);
        item.setCart(cart);

        cart.setItems(Collections.singletonList(item));

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        // Act
        BigDecimal result = checkoutService.getCartTotalPrice(cartId);

        // Assert
        assertEquals(new BigDecimal("20.00"), result);
        verify(cartRepository, times(1)).findById(cartId);
    }

    @Test
    void getCartTotalPrice_CartNotFound_ThrowsEntityNotFoundException() {
        // Arrange
        Long cartId = 1L;

        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> checkoutService.getCartTotalPrice(cartId));
        verify(cartRepository, times(1)).findById(cartId);
    }
}
