<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.nhnacademy.shoppingmall.product.service.ProductService" %>
<%@ page import="com.nhnacademy.shoppingmall.user.domain.Cart" %>
<%@ page import="java.util.List" %>
<%@ page import="com.nhnacademy.shoppingmall.product.domain.Product" %>
<%@ page import="com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl" %>
<%@ page import="com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl" %>
<html>
<head>
    <link href="img/favicon.ico" rel="icon">

    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Poppins&family=Roboto:wght@700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">
    <link href="lib/flaticon/font/flaticon.css" rel="stylesheet">

    <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

    <link href="css/bootstrap.min.css" rel="stylesheet">

    <link href="css/style.css" rel="stylesheet">

    <title>장바구니</title>
</head>
<body>

<div class="jumbotron" style="padding-top: 50px; padding-bottom: 50px">
    <div class="container">
        <h2>장바구니</h2>
    </div>
</div>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<div class="container">
    <div class="row" style="background-color: #f7f7f7; border-radius: 20px; height: 75px;">
        <table style="margin: 10px;">
            <tr>
                <td></td>
                <td style="padding-right: 35px;">
                    <a href="./shippingInfo.jsp" class="btn btn-success">주문하기	</a></td>
            </tr>
        </table>
    </div>
    <div style="padding-top: 50px">
        <table class="table table-hover">
            <tr>
                <th>상품</th>
                <th>가격</th>
                <th>수량</th>
                <th>비고</th>
            </tr>

<%
    ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    double sum = 0;
    List<Cart> cartList = (ArrayList<Cart>) request.getAttribute("cartList");
    if (cartList == null)
        cartList = new ArrayList<Cart>();

    for (int i = 0; i < cartList.size(); i++) {
        Cart cart = cartList.get(i);
        Product product = productService.findById(cart.getProductId()).get();
        double total = (product.getPrice() * cart.getAmount());
        sum = sum + total;
%>

<tr>
    <td><%=product.getProductId()%> - <%=product.getProductName()%></td>
    <td><%=product.getPrice()%></td>
    <td>
        <div class="input-group">
            <button class="btn btn-outline-secondary" type="button" onclick="updateQuantity(<%=i%>, 'decrease')">-</button>
            <input type="text" class="form-control" value="<%=cart.getAmount()%>" id="quantity_<%=i%>" readonly>
            <button class="btn btn-outline-secondary" type="button" onclick="updateQuantity(<%=i%>, 'increase')">+</button>
        </div>
    </td>
    <td></td>
    <td>
        <a href="/user/removeCart.do?productId=<%=product.getProductId()%>"
           class="bi bi-backspace-fill">&nbsp;삭제</a>
    </td>
</tr>
<%
    }
%>

<tr>
    <th></th>
    <th></th>
    <th>총액</th>
    <th id="sum" value="">${sum}</th>
    <th></th>
    <th></th>
</tr>
</table>
<a href="/index.do" class="btn btn-secondary"> &laquo; 쇼핑 계속하기</a>
</div>

<script>

    function updateQuantity(index, action) {
        let quantityElement = document.getElementById('quantity_' + index);
        let currentQuantity = parseInt(quantityElement.value);

        if (action === 'increase') {
            currentQuantity++;
        } else if (action === 'decrease' && currentQuantity > 1) {
            currentQuantity--;
        }

        quantityElement.value = currentQuantity;
    }
</script>


<%--<!-- footer -->--%>
<%--<%@ include file="footer.jsp"%>--%>

<!-- Back to Top -->

<!-- JavaScript Libraries -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="lib/easing/easing.min.js"></script>
<script src="lib/waypoints/waypoints.min.js"></script>
<script src="lib/owlcarousel/owl.carousel.min.js"></script>

<!-- Template Javascript -->
<script src="js/main.js"></script>

<!-- Cart -->
<script src="js/cart.js"></script>
</body>
</html>
