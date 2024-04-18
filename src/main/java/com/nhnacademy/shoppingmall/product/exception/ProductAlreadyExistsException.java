package com.nhnacademy.shoppingmall.product.exception;

public class ProductAlreadyExistsException extends RuntimeException {

    public ProductAlreadyExistsException(long productId){
        super(String.format("product already exist:%l",productId));
    }
}
