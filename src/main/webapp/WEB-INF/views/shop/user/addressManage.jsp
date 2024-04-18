<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kaeun
  Date: 2023/12/06
  Time: 8:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
    #addressList {
        list-style: none;
        padding: 0;
    }

    #addressList li {
        margin-bottom: 10px;
        border: 1px solid #ddd;
        padding: 10px;
        position: relative;
    }

    #addressList li button {
        background-color: #4CAF50;
        color: white;
        border: none;
        padding: 8px 16px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 14px;
        margin-left: 10px;
        cursor: pointer;
    }

    #addressList li form {
        display: inline;
    }

    #addressList li form input {
        display: none;
    }

    #addressList li:hover {
        background-color: #f9f9f9;
    }
</style>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="mypage-content" id="mypage-content">
    <!-- 배송지 목록 -->

    <h4>배송지 목록</h4>
    <ul id="addressList">
        <c:forEach var="address" items="${addressList}">
            <li>
                    ${address.addressInfo}
                <form action="/user/deleteAddress.do" method="post">
                    <input type="hidden" name="index" value="${address.addressId}">
                    <button type="submit">삭제</button>
                </form>
            </li>
        </c:forEach>
    </ul>

    <h4>새로운 배송지 추가</h4>
    <div class="input_area">
        <form action="/user/addressManagePost.do" method="post">
            <input type="text" placeholder="새로운 배송지 입력" id="newAddress" name="newAddress">
            <button type="submit">추가</button>
        </form>

    </div>

</div>
</body>
</html>
