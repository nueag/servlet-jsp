package com.nhnacademy.shoppingmall.controller.admin;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductCategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductCategoryService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductCategoryServiceImpl;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/productManagement.do")
public class ProductManagementController implements BaseController {
    private final ProductCategoryService
            productCategoryService = new ProductCategoryServiceImpl(new ProductCategoryRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int categoryIndex = 0;
        int currentPage = 0;
        if (Objects.isNull(req.getParameter("index"))) {
            categoryIndex = 1;
        } else {
            categoryIndex = Integer.parseInt(req.getParameter("index"));
        }
        req.setAttribute("index", categoryIndex);
        if (Objects.isNull(req.getParameter("page"))) {
            currentPage = 1;
        } else {
            currentPage = Integer.parseInt(req.getParameter("page"));
        }

        Page<Product> productPage = productCategoryService.findAllByCategoryId(currentPage, 9, categoryIndex);
        req.setAttribute("productList", productPage.getContent());
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("noOfPages", productPage.getTotalIndex());
        return "shop/admin/productManagement";
    }
}

