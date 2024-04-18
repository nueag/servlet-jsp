package com.nhnacademy.shoppingmall.common.filter;

import static com.nhnacademy.shoppingmall.user.domain.User.Auth.ROLE_ADMIN;

import com.nhnacademy.shoppingmall.user.domain.User;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebFilter(urlPatterns = "/admin/*")
public class AdminCheckFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        User user = (User) req.getSession(false).getAttribute("user");
        if (Objects.nonNull(user)) {
            if (user.getUserAuth().equals(ROLE_ADMIN)) {
                chain.doFilter(req, res);
            } else {
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "403 Forbidden");
            }
        }
    }
}
