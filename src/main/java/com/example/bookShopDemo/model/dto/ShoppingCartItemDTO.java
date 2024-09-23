package com.example.bookShopDemo.model.dto;

import lombok.Data;

@Data
public class ShoppingCartItemDTO {
    private BookDTO bookDTO;
    private Integer quantity;

    public ShoppingCartItemDTO(BookDTO bookDTO, Integer quantity) {
        this.bookDTO = bookDTO;
        this.quantity = quantity;
    }
}
