package com.nhnacademy.shoppingmall.controller.admin;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.CategoryService;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.CategoryServiceImpl;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/editProduct.do")
public class ProductEditController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
        CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());
        int productId = Integer.parseInt(req.getParameter("productId"));
        Product product = productService.findById(productId).get();
        req.setAttribute("product", product);
        req.setAttribute("categoryList", categoryService.getCategories());
        return "shop/admin/editProduct";
    }
}
