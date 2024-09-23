package com.example.bookShopDemo.service.impl;

import com.example.bookShopDemo.model.dto.ShoppingCartItemDTO;
import com.example.bookShopDemo.model.entity.Book;
import com.example.bookShopDemo.model.entity.ShoppingCart;
import com.example.bookShopDemo.model.entity.ShoppingCartItem;
import com.example.bookShopDemo.repository.BookRepository;
import com.example.bookShopDemo.repository.ShoppingCartRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ShoppingCartRepository cartRepository;

    @InjectMocks
    private ShoppingCartServiceImpl cartService;

    @BeforeEach
    void setUp() {
        //MockitoAnnotations.openMocks(this);
    }


    @Test
    void testAddToCart_BookNotFound_ThrowsException() {
        Long cartId = 1L;
        Long bookId = 1L;
        Integer quantity = 1;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> cartService.addToCart(cartId, bookId, quantity));
        assertEquals("Book not found with id " + bookId, exception.getMessage());

        verify(bookRepository, times(1)).findById(bookId);
        verify(cartRepository, never()).findById(anyLong());
    }

    @Test
    void testAddToCart_CartNotFound_CreatesNewCart() {
        Long cartId = 1L;
        Long bookId = 1L;
        Integer quantity = 1;

        Book book = new Book();
        book.setId(bookId);
        book.setPrice(BigDecimal.ONE);

        ShoppingCart cart = new ShoppingCart();
        cart.setId(cartId);
        cart.setItems(new LinkedList<>());

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());
        when(cartRepository.save(any(ShoppingCart.class))).thenReturn(cart);

        assertTrue(cartService.addToCart(cartId, bookId, quantity));

        verify(bookRepository, times(1)).findById(bookId);
        verify(cartRepository, times(1)).findById(cartId);
        verify(cartRepository, times(2)).save(any(ShoppingCart.class));
    }

    @Test
    void testAddToCart_CartFound_AddsItemToExistingCart() {
        Long cartId = 1L;
        Long bookId = 1L;
        Integer quantity = 1;

        Book book = new Book();
        book.setId(bookId);
        book.setPrice(BigDecimal.ONE);

        ShoppingCart cart = new ShoppingCart();
        cart.setId(cartId);
        cart.setItems(new LinkedList<>());

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        assertTrue(cartService.addToCart(cartId, bookId, quantity));

        verify(bookRepository, times(1)).findById(bookId);
        verify(cartRepository, times(1)).findById(cartId);
        verify(cartRepository, times(1)).save(cart);
        assertEquals(1, cart.getItems().size());
    }


    @Test
    void testGetItemsInCart_CartExists_ReturnsItems() {
        Long cartId = 1L;

        Book book = new Book();
        book.setId(1L);
        book.setPrice(BigDecimal.ONE);

        ShoppingCart cart = new ShoppingCart();
        cart.setId(cartId);
        LinkedList<ShoppingCartItem> items = new LinkedList<>();
        items.add(new ShoppingCartItem(book, 1));
        cart.setItems(items);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        /*when(BookMapper.INSTANCE.bookToBookDTO(any(Book.class))).thenAnswer(invocation -> {
            Book bookArg = invocation.getArgument(0);
            return new BookDTO(bookArg.getId() , bookArg.getCategory(), bookArg.getAuthor(), bookArg.getPrice(), bookArg.getTitle());
        });*/

        List<ShoppingCartItemDTO> result = cartService.getItemsInCart(cartId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getBookDTO().getId());
        assertEquals(1, result.get(0).getQuantity());

        verify(cartRepository, times(1)).findById(cartId);
    }

    @Test
    void testGetItemsInCart_CartDoesNotExist_ReturnsEmptyList() {
        Long cartId = 1L;

        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        List<ShoppingCartItemDTO> result = cartService.getItemsInCart(cartId);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(cartRepository, times(1)).findById(cartId);
    }

    @Test
    void testGetItemsInCart_CartExistsButNoItems_ReturnsEmptyList() {
        Long cartId = 1L;

        ShoppingCart cart = new ShoppingCart();
        cart.setId(cartId);
        cart.setItems(new LinkedList<>());

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        List<ShoppingCartItemDTO> result = cartService.getItemsInCart(cartId);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(cartRepository, times(1)).findById(cartId);
    }
}
