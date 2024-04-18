package com.nhnacademy.shoppingmall.product.repository;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> getProducts();

    Optional<Product> findById(int productId);

    int add(Product product);

    int deleteByProductId(int productId);

    int update(Product product);

    int countByProductId(int productId);

    long totalCount(Connection connection);

    Page<Product> findAll(int currentPage, int pageSize);

    int getLastInsertId();

}
