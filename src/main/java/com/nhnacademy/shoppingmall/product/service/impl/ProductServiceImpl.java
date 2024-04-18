package com.nhnacademy.shoppingmall.product.service.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.exception.ProductAlreadyExistsException;
import com.nhnacademy.shoppingmall.product.exception.ProductNotFoundException;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductCategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductCategoryService;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryService productCategoryService =
            new ProductCategoryServiceImpl(new ProductCategoryRepositoryImpl());

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.getProducts();
    }

    @Override
    public List<Product> getProductsByCategory(int categoryId) {
        //카테고리에서 상품 아이디 리스트를 가져와서
        //레파지토리에서 해당하는 상품을 찾아오는 for문을 돌리면 되지 않을까
        List<Integer> productIds = productCategoryService.getProductsByCategoryId(categoryId);
        List<Product> products = new ArrayList<>();
        for (int productId : productIds) {
            products.add(productRepository.findById(productId).get());
        }

        return products;
    }

    @Override
    public Optional<Product> findById(int productId) {

        return productRepository.findById(productId);
    }

    @Override
    public void addProduct(Product product, String[] categoryIds) {
        if (productRepository.countByProductId(product.getProductId()) < 1) {
            productRepository.add(product);
            for (String categoryId : categoryIds) {
                productCategoryService.addCategory(getLastInsertId(), Integer.parseInt(categoryId));
            }
        } else {
            throw new ProductAlreadyExistsException(product.getProductId());
        }
    }

    @Override
    public void updateProduct(Product product, String[] categoryIds) {
        if (productRepository.countByProductId(product.getProductId()) == 1) {
            productRepository.update(product);
            productCategoryService.updateCategory(product.getProductId(), categoryIds);
        } else {
            throw new ProductNotFoundException(product.getProductId());
        }
    }

    @Override
    public void deleteProduct(int productId) {
        productCategoryService.deleteCategory(productId);
        if (productRepository.countByProductId(productId) == 1) {
            productRepository.deleteByProductId(productId);
        } else {
            throw new ProductNotFoundException(productId);
        }
    }

    @Override
    public Page<Product> findAll(int currentPage, int pageSize) {
        Page<Product> productPage = productRepository.findAll(currentPage, pageSize);
        return productPage;
    }

    @Override
    public int getLastInsertId() {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select LAST_INSERT_ID()";

        ResultSet rs = null;
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            rs = statement.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (Objects.nonNull(rs)) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }


}
