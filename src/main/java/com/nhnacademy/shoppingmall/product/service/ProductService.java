package com.nhnacademy.shoppingmall.product.service;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getProducts();

    List<Product> getProductsByCategory(int categoryId);

    Optional<Product> findById(int productId);

    void addProduct(Product product, String[] categoryIds);

    void updateProduct(Product product, String[] categoryIds);

    void deleteProduct(int productId);

    Page<Product> findAll(int currentPage, int pageSize);

    int getLastInsertId();
}
