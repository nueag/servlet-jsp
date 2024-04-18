package com.nhnacademy.shoppingmall.user.repository;

import com.nhnacademy.shoppingmall.user.domain.Cart;
import java.util.List;

public interface CartRepository {
    List<Cart> getCarts();

    int addCart(String userId, int productId, int amount);

    int countByProductId(int productId);

    int deleteCart(int productId);
}
