package com.nhnacademy.shoppingmall.product.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductCategoryRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductCategoryRepositoryImpl implements ProductCategoryRepository {
    @Override
    public int addCategory(int productId, int categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into product_categories values(?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, categoryId);
            statement.setInt(2, productId);

            log.debug("product category sql = {}", sql);
            int result = statement.executeUpdate();
            log.debug("product category = {}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteCategory(int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from product_categories where product_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);

            int result = statement.executeUpdate();
            log.debug("result: {}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Integer> getProductsByCategoryId(int categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select product_id from product_categories where category_id = ?";

        ResultSet rs = null;
        List<Integer> productIds = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, categoryId);
            rs = statement.executeQuery();

            while (rs.next()) {
                productIds.add(rs.getInt(1));
            }

            return productIds;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long totalCount(Connection connection, int categoryId) {
        String sql = "select count(*) from product_categories where category_id=?";
        ResultSet rs = null;

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, categoryId);
            rs = psmt.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
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
        return 0l;
    }

    @Override
    public Page<Product> findAllByCategoryId(int currentPage, int pageSize, int categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        int offset = (currentPage - 1) * pageSize;
        int limit = pageSize;

        ResultSet rs = null;
        String sql =
                "select p.product_id, p.product_name, p.price, p.stock, p.product_image, p.product_info, p.register_date, p.latest_update_at from products as p " +
                        "inner join product_categories on p.product_id = product_categories.product_id " +
                        "where category_id = ? order by product_id desc limit ?, ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, categoryId);
            statement.setInt(2, offset);
            statement.setInt(3, limit);
            rs = statement.executeQuery();
            List<Product> products = new ArrayList<>(pageSize);

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getLong("price"),
                        rs.getInt("stock"),
                        rs.getString("product_image"),
                        rs.getString("product_info"),
                        rs.getTimestamp("register_date").toLocalDateTime(),
                        Objects.nonNull(rs.getTimestamp("latest_update_at")) ?
                                rs.getTimestamp("latest_update_at").toLocalDateTime() : null
                ));
            }
            long total = 0;
            if (!products.isEmpty()) {
                // size>0 조회 시도, 0이면 조회할 필요 없음, count query는 자원을 많이 소모하는 작업
                total = totalCount(connection, categoryId);
            }
            return new Page<Product>(products, pageSize, total);
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
