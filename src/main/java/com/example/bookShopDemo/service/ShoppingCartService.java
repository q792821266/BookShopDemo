package com.example.bookShopDemo.service;

import com.example.bookShopDemo.model.dto.ShoppingCartItemDTO;

import java.util.List;

public interface ShoppingCartService {

   Boolean addToCart(Long cartId, Long bookId,Integer quantity);

   List<ShoppingCartItemDTO> getItemsInCart(Long cartId);

   Boolean removeFromCart(Long cartId, Long bookId,Integer quantity);
}
