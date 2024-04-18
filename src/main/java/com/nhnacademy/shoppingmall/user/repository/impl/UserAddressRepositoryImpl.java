package com.nhnacademy.shoppingmall.user.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.UserAddress;
import com.nhnacademy.shoppingmall.user.repository.UserAddressRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAddressRepositoryImpl implements UserAddressRepository {
    public UserAddressRepositoryImpl() {
    }

    @Override
    public int add(int addressId, String userId, String addressInfo) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into user_address (user_id, address_info) values (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userId);
            statement.setString(2, addressInfo);

            int result = statement.executeUpdate();
            log.debug("user_address add result: {}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserAddress> getAddress() {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from user_address";

        ResultSet rs = null;
        List<UserAddress> addressList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            rs = statement.executeQuery();
            while (rs.next()) {
                addressList.add(new UserAddress(
                                rs.getInt("address_id"),
                                rs.getString("user_id"),
                                rs.getString("address_info")
                        )
                );
            }
            return addressList;
        } catch (SQLException e) {

        } finally {
            try {
                if (Objects.isNull(rs)) {
                    rs.close();
                }
            } catch (SQLException e) {

            }
        }
        return null;
    }

    @Override
    public int delete(int addressId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from user_address where address_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, addressId);
            int result = statement.executeUpdate();
            return result;
        } catch (SQLException e) {

        }
        return -1;
    }
}
