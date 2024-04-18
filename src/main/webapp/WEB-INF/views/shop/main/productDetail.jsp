<%@ page import="java.util.Objects" %><%--
  Created by IntelliJ IDEA.
  User: kaeun
  Date: 2023/12/06
  Time: 3:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>상품 상세 정보</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      /*display: flex;*/
      justify-content: center;
      align-items: center;
      height: 100vh;
      margin: 0;
    }

    .product-detail {
      display: flex;
      max-width: 800px;
      margin: auto;
      border: 1px solid #ddd;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      overflow: hidden;
    }

    .product-image {
      flex: 1;
      text-align: center;
    }

    .product-image img {
      max-width: 100%;
      max-height: 100%;
      object-fit: cover;
    }

    .product-info {
      flex: 1;
      padding: 20px;
    }

    .product-info h2 {
      font-size: 24px;
      margin-bottom: 10px;
    }

    .product-info p {
      font-size: 16px;
      margin: 10px 0;
    }

    .description {
      margin-top: 20px;
    }

    .buttons {
      margin-top: 20px;
    }

    .buttons button {
      padding: 10px;
      font-size: 16px;
      cursor: pointer;
      background-color: #4CAF50;
      color: white;
      border: none;
    }
  </style>
</head>
<body>

<div class="product-detail">
  <div class="product-image">
    <img src="${product.productImage}" alt="${product.productName}">
  </div>

  <div class="product-info">
    <h2>${product.productName}</h2>
    <p class="price">${product.price} 원</p>
    <p class="stock">재고: ${product.stock} 개</p>

    <div class="description">
      <h3>상세 설명</h3>
      <p>${product.productInfo}</p>
    </div>


    <div class="buttons">
      <button type="button" class="buy-button" name="productId" value="${product.productId}" onclick="redirectToPurchasePage()">구매하기</button>
      <button type="button" class="cart-button" name="productId2" value="${product.productId}" onclick="redirectToCartPage()">장바구니에 담기</button>

      <script>
        function redirectToPurchasePage() {
          <%
            Object userId = session.getAttribute("user_id");
            if(userId == null) {
          %>

          alert("로그인 후 이용가능합니다.");
          <% } else { %>
          window.location.href = '/purchase.do?productId=${product.productId}';
          <% } %>
        }

        function redirectToCartPage() {
          <%
            Object userId2 = session.getAttribute("user_id");
            if(userId2 == null) {
          %>

            alert("로그인 후 이용가능합니다.");
          <% } else { %>
          window.location.href = '/addCart.do?productId=${product.productId}';
          <% } %>
        }
      </script>
    </div>
  </div>
  <a href="/index.do" class="btn btn-secondary"> &laquo; 쇼핑 계속하기</a>
</div>

</body>
</html>

