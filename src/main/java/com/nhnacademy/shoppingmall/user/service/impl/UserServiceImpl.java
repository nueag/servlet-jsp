package com.nhnacademy.shoppingmall.user.service.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String userId) {
        if (Objects.isNull(userId)) {
            throw new IllegalStateException("input parameter is null");
        }
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    @Override
    public void saveUser(User user) {
        if (Objects.isNull(user)) {
            throw new IllegalStateException("input parameter is null");
        }
        if (userRepository.countByUserId(user.getUserId()) < 1) {
            userRepository.save(user);
        } else {
            throw new UserAlreadyExistsException("user already exists exception!");
        }
    }

    @Override
    public void updateUser(User user) {
        if (Objects.isNull(user)) {
            throw new IllegalStateException("input parameter is null");
        }
        if (userRepository.countByUserId(user.getUserId()) == 1) {
            userRepository.update(user);
            userRepository.updateLatestLoginAtByUserId(user.getUserId(), LocalDateTime.now());
        }
    }

    @Override
    public void deleteUser(String userId) {
        if (Objects.isNull(userId)) {
            throw new IllegalStateException("input parameter is null");
        }
        if (userRepository.countByUserId(userId) == 1) {
            userRepository.deleteByUserId(userId);
        }
    }

    @Override
    public User doLogin(String userId, String userPassword) {
        if (Objects.isNull(userId) || Objects.isNull(userPassword)) {
            throw new IllegalStateException("input parameter is null");
        }
        Optional<User> user = userRepository.findByUserIdAndUserPassword(userId, userPassword);
        if (user.isPresent()) {
            userRepository.updateLatestLoginAtByUserId(userId, LocalDateTime.now());
            return user.get();
        }
        throw new UserNotFoundException("user not found exception!   ");
    }

    @Override
    public boolean isAdmin(String userId) {
        return false;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    @Override
    public Page<User> findAll(int currentPage, int pageSize) {
        return userRepository.findAll(currentPage, pageSize);
    }


}
