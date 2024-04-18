package com.nhnacademy.shoppingmall.product.repository;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import java.sql.Connection;
import java.util.List;

public interface ProductCategoryRepository {
    int addCategory(int productId, int categoryId);

    int deleteCategory(int productId);

    List<Integer> getProductsByCategoryId(int categoryId);

    long totalCount(Connection connectio, int categoryId);

    Page<Product> findAllByCategoryId(int currentPage, int pageSize, int categoryId);
}
