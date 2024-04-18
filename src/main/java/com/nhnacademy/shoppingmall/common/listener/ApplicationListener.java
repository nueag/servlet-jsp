package com.nhnacademy.shoppingmall.common.listener;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import java.time.LocalDateTime;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebListener
public class ApplicationListener implements ServletContextListener {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            DbConnectionThreadLocal.initialize();
            ServletContext context = sce.getServletContext();
            User user = new User("user", "user", "12345", "19900505", User.Auth.ROLE_USER, 100_0000,
                    LocalDateTime.now(), null);
            User admin = new User("admin", "admin", "12345", "20010927", User.Auth.ROLE_ADMIN, 100_0000,
                    LocalDateTime.now(), null);
            userService.saveUser(user);
            userService.saveUser(admin);
            addBeautyProducts();

            context.setAttribute("userService", userService);
        } catch (Exception e) {

        } finally {
            DbConnectionThreadLocal.reset();
        }
    }

    public void addBeautyProducts() {
        String[] category = {"1"};
        Product product1 = new Product(1, "VT 마일드 리들샷 50", 27000, 300,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSqdrWt4eGyXkC5WeUq_P_f1gKusvnFI2dWkNaf-76NokubBsXZ_HazDZJyqFNtfF8D0eo&usqp=CAU",
                "촉촉한 로션", LocalDateTime.now(), null);
        Product product2 = new Product(2, "지베르니 밀착 커버 파운데이션 30ml", 22300, 300,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQFLGq1zG-rqaRFgwWEuMz7-NLVEUVNcYGapF5GTKxMmu5EOARh1G3BKXnHkN2_Vs3hqqk&usqp=CAU",
                "밀착력 좋은 파운데이션", LocalDateTime.now(), null);
        Product product3 = new Product(3, "닥터지 닥터지 레드 블레미쉬 쿨 수딩 마스크", 14000, 300,
                "https://image.msscdn.net/images/goods_img/20191209/1247154/1247154_16902521062961_500.jpg",
                "수분 많은 수분팩", LocalDateTime.now(), null);
        Product product4 = new Product(4, "이니스프리 New 블랙티 유스 인핸싱 탄력 크림", 19840, 300,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcStlMm5sGlsOA_NNrhfHYdnEZm_xxz9dFA4SdXvnK1V7U-wAhwglDpZWFv9uctLOhq09eY&usqp=CAU",
                "탄력 크림", LocalDateTime.now(), null);
        Product product5 = new Product(5, "닥터지 레드 블레미쉬 시카 수딩 크림 듀오 기획세트", 31000, 300,
                "https://fs.dr-g.co.kr/item/4789/4789-add1.jpg", "촉촉한 크림", LocalDateTime.now(), null);
        Product product6 = new Product(6, "헤라 블랙 쿠션 15g", 43000, 300,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTnnGhxDH7to1aFb3k2KgIJ5SX5w-wU0uYtwgAvmsSAjwswrFXRB-l7rBrrdZ5AvIZ02A8&usqp=CAU",
                "헤라 쿠션", LocalDateTime.now(), null);

        productService.addProduct(product1, category);
        productService.addProduct(product2, category);
        productService.addProduct(product3, category);
        productService.addProduct(product4, category);
        productService.addProduct(product5, category);
        productService.addProduct(product6, category);

    }
}
