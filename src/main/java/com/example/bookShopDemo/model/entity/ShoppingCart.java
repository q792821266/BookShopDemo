package com.example.bookShopDemo.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.util.Assert;

import java.util.LinkedList;
import java.util.List;

@Entity
@Data
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShoppingCartItem> items = new LinkedList<>();

    public void addItem(Book book, Integer quantity) {
        Assert.notNull(book, "Book must not be null");
        Assert.state(quantity > 0, "Quantity must be greater than zero");

        for (ShoppingCartItem item : items) {
            if (book.getId().equals(item.getBook().getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        ShoppingCartItem newItem = new ShoppingCartItem(book, quantity);
        newItem.setCart(this);
        items.add(newItem);
    }

    public void addItem(Book book) {

        //createItems();
        boolean found = false;
        for (ShoppingCartItem item : items) {
            if (item.getBook().getId().equals(book.getId())) {
                item.setQuantity(item.getQuantity() + 1);
                found = true;
                break;
            }
        }
        if (!found) {
            ShoppingCartItem newItem = new ShoppingCartItem();
            newItem.setBook(book);
            newItem.setQuantity(1);
            //newItem.setCart(this);
            items.add(newItem);
        }
    }

    protected List<ShoppingCartItem> createItems() {
        if (items == null) {
            synchronized (ShoppingCart.class) {
                if (items == null) {
                    items = new LinkedList<>();
                }
            }
        }
        return items;
    }

    public void removeItem(Book book, Integer quantity) {
        Assert.notNull(book, "Book must not be null");
        Assert.state(quantity > 0, "Quantity must be greater than zero");
        for (ShoppingCartItem item : items) {
            if (book.getId().equals(item.getBook().getId())) {
                item.setQuantity(item.getQuantity() - quantity);
                if (item.getQuantity() <= 0) {
                    items.remove(item);
                }
            }
        }
    }

}

