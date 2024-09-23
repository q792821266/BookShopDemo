package com.example.bookShopDemo.service;

import com.example.bookShopDemo.model.dto.ShoppingCartItemDTO;
import com.example.bookShopDemo.model.entity.ShoppingCartItem;

import java.util.List;

public interface ShoppingCartService {

   Boolean addToCart(Long cartId, Long bookId,Integer quantity);

   List<ShoppingCartItemDTO> getItemsInCart(Long cartId);

   Boolean removeFromCart(Long cartId, Long bookId,Integer quantity);
}
