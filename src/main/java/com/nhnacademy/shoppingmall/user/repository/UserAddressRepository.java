package com.nhnacademy.shoppingmall.user.repository;

import com.nhnacademy.shoppingmall.user.domain.UserAddress;
import java.util.List;

public interface UserAddressRepository {
    int add(int productId, String userId, String addressInfo);

    List<UserAddress> getAddress();

    int delete(int addressId);

}
