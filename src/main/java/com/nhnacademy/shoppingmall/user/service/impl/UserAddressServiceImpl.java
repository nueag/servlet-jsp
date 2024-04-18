package com.nhnacademy.shoppingmall.user.service.impl;

import com.nhnacademy.shoppingmall.user.domain.UserAddress;
import com.nhnacademy.shoppingmall.user.repository.UserAddressRepository;
import com.nhnacademy.shoppingmall.user.service.UserAddressService;
import java.util.List;

public class UserAddressServiceImpl implements UserAddressService {
    private UserAddressRepository userAddressRepository;

    public UserAddressServiceImpl(UserAddressRepository userAddressRepository) {
        this.userAddressRepository = userAddressRepository;
    }

    @Override
    public void add(String userId, String addressInfo) {
        userAddressRepository.add(0, userId, addressInfo);
    }

    @Override
    public List<UserAddress> getAddress() {
        return userAddressRepository.getAddress();
    }

    @Override
    public void delete(int addressId) {
        if (userAddressRepository.delete(addressId) < 1) {
            throw new RuntimeException("delete fail");
        }
    }
}
