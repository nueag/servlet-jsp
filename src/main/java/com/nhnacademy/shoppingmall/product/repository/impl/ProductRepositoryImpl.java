package com.nhnacademy.shoppingmall.product.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductRepositoryImpl implements ProductRepository {
    public static int productIdCount = 0;

    @Override
    public List<Product> getProducts() {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from products";
        log.debug("sql: {}", sql);

        ResultSet rs = null;
        List<Product> products = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            rs = statement.executeQuery();
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
            return products;
        } catch (SQLException e) {
            System.out.println(e);
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

    @Override
    public Optional<Product> findById(int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql =
                "select * from products where product_id=?";
        log.debug("sql:{}", sql);

        ResultSet rs = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, productId);
            rs = statement.executeQuery();
            if (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getLong("price"),
                        rs.getInt("stock"),
                        rs.getString("product_image"),
                        rs.getString("product_info"),
                        rs.getTimestamp("register_date").toLocalDateTime(),
                        Objects.nonNull(rs.getTimestamp("latest_update_at")) ?
                                rs.getTimestamp("latest_update_at").toLocalDateTime() : null
                );
                return Optional.of(product);
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
        return Optional.empty();
    }

    @Override
    public int add(Product product) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql =
                "insert into products (product_name, price, stock, product_image, product_info, register_date, latest_update_at) " +
                        "values (?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setLong(1, product.getProductId());
            statement.setString(1, product.getProductName());
            statement.setDouble(2, product.getPrice());
            statement.setLong(3, product.getStock());
            statement.setString(4, product.getProductImage());
            statement.setString(5, product.getProductInfo());
            statement.setString(6, product.getRegisterDate().toString());
            statement.setString(7,
                    Objects.nonNull(product.getLatestUpdateAt()) ? product.getLatestUpdateAt().toString() : null);

            log.debug("product sql : {}", sql);
            int result = statement.executeUpdate();
            log.debug("product result : {}", result);
//            productIdCount++;
            return result;
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByProductId(int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from products where product_id=?";
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
    public int update(Product product) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql =
                "update products set product_name=?, price=?, stock=?, product_image=?, product_info=?, register_date=?, latest_update_at=? where product_id=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getProductName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getStock());
            statement.setString(4, product.getProductImage());
            statement.setString(5, product.getProductInfo());
            statement.setString(6, product.getRegisterDate().toString());
            statement.setString(7,
                    Objects.nonNull(product.getLatestUpdateAt()) ? product.getLatestUpdateAt().toString() : null);
            statement.setInt(8, product.getProductId());

            int result = statement.executeUpdate();
            log.debug("result: {}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByProductId(int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) from products where product_id=?";

        ResultSet rs = null;
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, productId);
            rs = statement.executeQuery();

            if (rs.next()) {
                result = rs.getInt(1);
            }
            log.debug("result: {}", result);
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

    @Override
    public long totalCount(Connection connection) {
        String sql = "select count(*) from products";
        ResultSet rs = null;

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
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
    public Page<Product> findAll(int currentPage, int pageSize) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        int offset = (currentPage - 1) * pageSize;
        int limit = pageSize;

        ResultSet rs = null;
        String sql = "select * from products order by product_id desc limit ?, ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, offset);
            statement.setInt(2, limit);
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
                total = totalCount(connection);
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
