package com.example.bookShopDemo.service;

import java.math.BigDecimal;

public interface CheckoutService {
   BigDecimal getCartTotalPrice(Long cartId);
}
