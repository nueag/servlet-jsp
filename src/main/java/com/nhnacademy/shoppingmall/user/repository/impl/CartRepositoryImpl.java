package com.nhnacademy.shoppingmall.user.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.Cart;
import com.nhnacademy.shoppingmall.user.repository.CartRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartRepositoryImpl implements CartRepository {
    @Override
    public List<Cart> getCarts() {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from cart";

        ResultSet rs = null;
        List<Cart> carts = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            rs = statement.executeQuery();
            while (rs.next()) {
                carts.add(new Cart(
                        rs.getInt("cart_id"),
                        rs.getInt("product_id"),
                        rs.getInt("amount")
                ));
            }
            return carts;
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
    public int addCart(String userId, int productId, int amount) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into cart (product_id, user_id, amount) values (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            statement.setString(2, userId);
            statement.setInt(3, amount);
            int result = statement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByProductId(int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) from cart where product_id = ?";

        ResultSet rs = null;
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
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

    @Override
    public int deleteCart(int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from cart where product_id=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public int addCart(Cart cart) {
//        Connection connection = DbConnectionThreadLocal.getConnection();
//        String sql = "insert into cart (product_id, amount) values (?, ?)";
//
//        try(PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setInt(1, cart.getProductId());
//            statement.setInt(2, cart.getAmount());
//            int result = statement.executeUpdate();
//            return result;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
