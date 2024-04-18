package com.nhnacademy.shoppingmall.product.service.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductCategoryRepository;
import com.nhnacademy.shoppingmall.product.service.ProductCategoryService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public void addCategory(int productId, int categoryId) {
        if (productCategoryRepository.addCategory(productId, categoryId) < 1) {
            throw new RuntimeException("productCategory add fail");
        }
    }

    @Override
    public void deleteCategory(int productId) {
        productCategoryRepository.deleteCategory(productId);
    }

    @Override
    public void updateCategory(int productId, String[] categoryIds) {
        productCategoryRepository.deleteCategory(productId);
        for (String categoryId : categoryIds) {
            productCategoryRepository.addCategory(productId, Integer.parseInt(categoryId));
        }
    }

    @Override
    public List<Integer> getProductsByCategoryId(int categoryId) {
        return productCategoryRepository.getProductsByCategoryId(categoryId);
    }

    @Override
    public Page<Product> findAllByCategoryId(int currentPage, int pageSize, int categoryId) {
        return productCategoryRepository.findAllByCategoryId(currentPage, pageSize, categoryId);
    }
}
