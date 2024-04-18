package com.nhnacademy.shoppingmall.user.service;

import com.nhnacademy.shoppingmall.user.domain.Cart;
import java.util.List;

public interface CartService {
    List<Cart> getCarts();

    void addCart(String userId, int productId, int amount);

    boolean isExist(int productId);

    void deleteCart(int productId);
}
