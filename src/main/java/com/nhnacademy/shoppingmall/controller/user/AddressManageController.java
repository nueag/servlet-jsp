package com.nhnacademy.shoppingmall.controller.user;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.CategoryService;
import com.nhnacademy.shoppingmall.product.service.impl.CategoryServiceImpl;
import com.nhnacademy.shoppingmall.user.repository.impl.UserAddressRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserAddressService;
import com.nhnacademy.shoppingmall.user.service.impl.UserAddressServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.GET, value = "/user/addressManage.do")
public class AddressManageController implements BaseController {
    private final UserAddressService userAddressService = new UserAddressServiceImpl(new UserAddressRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        req.getServletContext().setAttribute("addressList", userAddressService.getAddress());
        return "shop/user/addressManage";
    }
}
