package com.example.bookShopDemo.service.impl;

import com.example.bookShopDemo.model.entity.ShoppingCart;
import com.example.bookShopDemo.repository.ShoppingCartRepository;
import com.example.bookShopDemo.service.CheckoutService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final ShoppingCartRepository cartRepository;

    public CheckoutServiceImpl(ShoppingCartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public BigDecimal getCartTotalPrice(Long cartId) {
        ShoppingCart cart = cartRepository.findById(cartId).orElseThrow(() -> new EntityNotFoundException("Cart not found with id " + cartId));
        return cart.getItems().stream()
                .map(item -> item.getBook().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
