package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.POST, value = "/loginAction.do")
public class LoginPostController implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().setMaxInactiveInterval(60 * 60);
        String userId = req.getParameter("user_id");
        String userPassword = req.getParameter("user_password");

        try {
            userService.doLogin(userId, userPassword);
        } catch (UserNotFoundException e) {
            return "redirect:/login.do";
        }
        User user = userService.getUser(userId);
        if (user.getUserAuth().equals(User.Auth.ROLE_ADMIN)) {
            req.getSession().setAttribute("user_id", userId);
            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("isAdmin", true);
            return "redirect:/admin/admin_home.do";
        }

        req.getSession().setAttribute("user_id", userId);
        req.getSession().setAttribute("user", user);
        req.setAttribute("productList", productService.getProducts());
        return "redirect:/index.do";
    }
}
