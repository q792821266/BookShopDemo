package com.example.bookShopDemo.controller;

import com.example.bookShopDemo.model.dto.ShoppingCartItemDTO;
import com.example.bookShopDemo.model.entity.ShoppingCartItem;
import com.example.bookShopDemo.repository.BookRepository;
import com.example.bookShopDemo.repository.ShoppingCartRepository;
import com.example.bookShopDemo.service.CheckoutService;
import com.example.bookShopDemo.service.ShoppingCartService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/shopping-cart")
public class ShoppingCartController {


    private final ShoppingCartService shoppingCartService;
    private final CheckoutService checkoutService;

    public ShoppingCartController(ShoppingCartService shoppingCartService, CheckoutService checkoutService) {
        this.shoppingCartService = shoppingCartService;
        this.checkoutService = checkoutService;
    }

    @PostMapping("/add/{shopping-cartId}")
    public ResponseEntity<Boolean> addToCart(@PathVariable("shopping-cartId") Long shoppingCartId, @RequestParam Long bookId, @RequestParam @Min(1) Integer quantity) {
        Boolean success = shoppingCartService.addToCart(shoppingCartId, bookId, quantity);
        return ResponseEntity.ok(success);
    }

    @PostMapping("/remove/{shopping-cartId}")
    public ResponseEntity<Boolean> removeFromCart(@PathVariable("shopping-cartId") Long shoppingCartId, @RequestParam Long bookId, @RequestParam @Min(1) Integer quantity) {
        Boolean success = shoppingCartService.removeFromCart(shoppingCartId, bookId, quantity);
        return ResponseEntity.ok(success);
    }

    @GetMapping("/{shopping-cartId}")
    public ResponseEntity<List<ShoppingCartItemDTO>> getCart(@PathVariable("shopping-cartId") Long shoppingCartId) {
        return ResponseEntity.ok(shoppingCartService.getItemsInCart(shoppingCartId));
    }


    @GetMapping("/checkout/{shopping-cartId}")
    public ResponseEntity<BigDecimal> getTotalPrice(@PathVariable("shopping-cartId") Long shoppingCartId) {
        BigDecimal totalPrice = checkoutService.getCartTotalPrice(shoppingCartId);
        return ResponseEntity.ok(totalPrice);

    }
}