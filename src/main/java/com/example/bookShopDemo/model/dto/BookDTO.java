package com.example.bookShopDemo.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookDTO {

    private Long id;
    @NotNull(message = "title cannot be null")
    private String title;
    @NotNull(message = "author cannot be null")
    private String author;

    @DecimalMin(value = "0", inclusive = false)
    private BigDecimal price;
    private String category;

    public BookDTO(Long id, String title, String author, BigDecimal price, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.category = category;
    }
}