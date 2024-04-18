package com.nhnacademy.shoppingmall.user.service;

import com.nhnacademy.shoppingmall.user.domain.UserAddress;
import java.util.List;

public interface UserAddressService {
    void add(String userId, String addressInfo);

    List<UserAddress> getAddress();

    void delete(int addressId);
}
