package com.nhnacademy.shoppingmall.controller.admin;

import static java.lang.Double.parseDouble;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/add_product_post.do")
public class ProductAddPostController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    private static final String UPLOAD_DIR =
            "/Users/kaeun/github/LevelTest02/java-servlet-jsp-shoppingmall/src/main/webapp/upload";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        log.debug("들어옴");
        int productId = 0;
        String productName = req.getParameter("productName");
        log.debug("productName: {}", productName);
        String price = (req.getParameter("price"));
        String stock = (req.getParameter("stock"));
        String[] categories = req.getParameterValues("category");
        String productImage = "";
        String productInfo = req.getParameter("productInfo");

        for (Part part : req.getParts()) {
            String contentDisposition = part.getHeader(CONTENT_DISPOSITION);
            log.debug("contentDisposition: {}", contentDisposition);
            if (contentDisposition.contains("filename=")) {
                String fileName = extractFileName(contentDisposition);

                if (part.getSize() > 0) {
                    part.write(UPLOAD_DIR + File.separator + fileName);
                    productImage = "/upload" + File.separator + fileName;
                    part.delete();
                } else {
                    productImage = "/resources" + File.separator + "no-image.png";
                }
            } else {
                String formValue = req.getParameter(part.getName());
                log.error("{}={}", part.getName(), formValue);
            }
        }


        Product product = new Product(productId, productName, parseDouble(price), Integer.parseInt(stock), productImage,
                productInfo,
                LocalDateTime.now(), null);
        productService.addProduct(product, categories);

        return "redirect:/admin/add_product.do";
    }

    private String extractFileName(String contentDisposition) {
        log.error("contentDisposition:{}", contentDisposition);
        for (String token : contentDisposition.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return null;
    }
}