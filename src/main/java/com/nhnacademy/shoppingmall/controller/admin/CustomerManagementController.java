package com.nhnacademy.shoppingmall.controller.admin;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductCategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductCategoryService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductCategoryServiceImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/customerManagement.do")
public class CustomerManagementController implements BaseController {
    private final ProductCategoryService
            productCategoryService = new ProductCategoryServiceImpl(new ProductCategoryRepositoryImpl());

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int currentPage = 0;
        if (Objects.isNull(req.getParameter("page"))) {
            currentPage = 1;
        } else {
            currentPage = Integer.parseInt(req.getParameter("page"));
        }

        Page<User> userPage = userService.findAll(currentPage, 9);
        req.setAttribute("productList", userPage.getContent());
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("noOfPages", userPage.getTotalIndex());
        req.setAttribute("userList", userService.getUsers());

        return "shop/admin/customerManagement";
    }
}
