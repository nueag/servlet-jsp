package com.nhnacademy.shoppingmall.thread.request.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.thread.request.ChannelRequest;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PointChannelRequest extends ChannelRequest {
    private int POINT;
    private User user;

    public PointChannelRequest(User user, int point) {
        this.user = user;
        this.POINT = point;
    }

    @Override
    public void execute() {
        DbConnectionThreadLocal.initialize();
        UserRepository userRepository = new UserRepositoryImpl();
        UserService userService = new UserServiceImpl(userRepository);
        int updatePoint = user.getUserPoint() + this.POINT;
        User updateUser = new User(user.getUserId(), user.getUserName(), user.getUserPassword(),
                user.getUserBirth(), user.getUserAuth(), updatePoint, user.getCreatedAt(), user.getLatestLoginAt());
        userService.updateUser(updateUser);
        log.debug("pointChannel execute");
        DbConnectionThreadLocal.reset();
    }
}
