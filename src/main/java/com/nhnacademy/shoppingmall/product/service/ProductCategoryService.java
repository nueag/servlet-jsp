package com.nhnacademy.shoppingmall.product.service;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import java.util.List;

public interface ProductCategoryService {
    void addCategory(int productId, int categoryId);

    void deleteCategory(int productId);

    void updateCategory(int productId, String[] categoryId);

    List<Integer> getProductsByCategoryId(int categoryId);

    Page<Product> findAllByCategoryId(int currentPage, int pageSize, int categoryId);
}
