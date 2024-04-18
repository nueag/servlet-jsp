package com.nhnacademy.shoppingmall.controller.user;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.POST, value = "/user/profileUpdatePost.do")
public class ProfileUpdatePostController implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");
        String userPassword = req.getParameter("userPwd");
        String passwordCheck = req.getParameter("userPwd_re");
        String userName = req.getParameter("userName");
        String userBirth = req.getParameter("birth");

        if (!userPassword.equals(passwordCheck)) {
            req.setAttribute("isFinished", false);
            return "shop/login/sign_up";
        }

        User user =
                new User(userId, userName, userPassword, userBirth, User.Auth.ROLE_USER, 1_000_000, LocalDateTime.now(),
                        null);
        userService.updateUser(user);

        req.setAttribute("isFinished", true);

        return "shop/user/userHome";
    }
}
