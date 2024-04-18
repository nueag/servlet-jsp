package com.nhnacademy.shoppingmall.controller.user;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.user.domain.Cart;
import com.nhnacademy.shoppingmall.user.repository.impl.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.CartService;
import com.nhnacademy.shoppingmall.user.service.impl.CartServiceImpl;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/user/myCart.do")
public class MyCartController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final CartService cartService = new CartServiceImpl(new CartRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        double sum = 0;
        List<Cart> cartList = cartService.getCarts();
        if (cartList == null) {
            cartList = new ArrayList<Cart>();
        }

        for (int i = 0; i < cartList.size(); i++) { // 상품리스트 하나씩 출력하기
            Cart cart = cartList.get(i);
            Product product = productService.findById(cart.getProductId()).get();
            double total = (product.getPrice() * cart.getAmount());
            sum = sum + total;
        }

        req.setAttribute("cartList", cartService.getCarts());
        req.setAttribute("sum", sum);
        return "shop/user/myCart";
    }
}