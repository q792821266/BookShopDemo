package com.example.bookShopDemo.service.impl;

import com.example.bookShopDemo.mapper.BookMapper;
import com.example.bookShopDemo.model.dto.ShoppingCartItemDTO;
import com.example.bookShopDemo.model.entity.Book;
import com.example.bookShopDemo.model.entity.ShoppingCart;
import com.example.bookShopDemo.model.entity.ShoppingCartItem;
import com.example.bookShopDemo.repository.BookRepository;
import com.example.bookShopDemo.repository.ShoppingCartRepository;
import com.example.bookShopDemo.service.ShoppingCartService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final BookRepository bookRepository;
    private final ShoppingCartRepository cartRepository;

    public ShoppingCartServiceImpl(BookRepository bookRepository, ShoppingCartRepository cartRepository) {
        this.bookRepository = bookRepository;
        this.cartRepository = cartRepository;
    }

    public ShoppingCart createCart() {
        return cartRepository.save(new ShoppingCart());
    }

    private ShoppingCart getOrCreateCart(Long cartId) {
        return cartRepository.findById(cartId).orElseGet(this::createCart);
    }

    @Override
    public Boolean addToCart(Long cartId, Long bookId, Integer quantity) {
        Assert.state(quantity > 0, "Quantity must be greater than zero");
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found with id " + bookId));
        ShoppingCart cart = getOrCreateCart(cartId);
        cart.addItem(book, quantity);
        cartRepository.save(cart);
        return Boolean.TRUE;
    }

    @Override
    public List<ShoppingCartItemDTO> getItemsInCart(Long cartId) {
        ShoppingCart cart = getOrCreateCart(cartId);
        if (cart != null) {
            return cart.getItems().stream()
                    .map(item -> new ShoppingCartItemDTO(BookMapper.INSTANCE.bookToBookDTO(item.getBook()), item.getQuantity()))
                    .toList();
        }
        return List.of();
    }

    @Override
    public Boolean removeFromCart(Long cartId, Long bookId, Integer quantity) {
        Assert.state(quantity > 0, "Quantity must be greater than zero");
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found with id " + bookId));
        ShoppingCart cart = getOrCreateCart(cartId);
        cart.removeItem(book, quantity);
        return Boolean.TRUE;
    }


}
