¬<%--
  Created by IntelliJ IDEA.
  User: kaeun
  Date: 2023/12/04
  Time: 1:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>관리자 페이지</title>
</head>
<body>
<h2>관리자 페이지</h2>


<p>안녕하세요, 관리자님!</p>

<ul>
    <li><a href="/admin/productManagement.do?page=1">상품 관리</a></li>
    <li><a href="/admin/orderManagement.do">주문 관리</a></li>
    <li><a href="/admin/customerManagement.do">고객 관리</a></li>
</ul>


<a href="Logout.jsp">로그아웃</a>
</body>
</html>
