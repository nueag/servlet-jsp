package com.nhnacademy.shoppingmall.user.service.impl;

import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.user.domain.Cart;
import com.nhnacademy.shoppingmall.user.repository.CartRepository;
import com.nhnacademy.shoppingmall.user.repository.impl.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.CartService;
import java.util.List;

public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }
    @Override
    public List<Cart> getCarts() {
        return cartRepository.getCarts();
    }

    @Override
    public void addCart(String userId, int productId, int amount) {
        //중복된 상품 아이디가 있는 경우에는 종료한다.
        if(!isExist(productId)) {
            cartRepository.addCart(userId, productId, amount);
        }
    }

    @Override
    public boolean isExist(int productId) {
        if(cartRepository.countByProductId(productId) > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void deleteCart(int productId) {
        cartRepository.deleteCart(productId);
    }
}
