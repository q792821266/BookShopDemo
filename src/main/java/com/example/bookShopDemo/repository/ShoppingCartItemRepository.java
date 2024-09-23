package com.example.bookShopDemo.repository;

import com.example.bookShopDemo.model.entity.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem,Long> {
}
