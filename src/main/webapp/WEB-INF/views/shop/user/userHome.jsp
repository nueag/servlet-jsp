<%--
  Created by IntelliJ IDEA.
  User: kaeun
  Date: 2023/12/06
  Time: 8:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>마이페이지</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .mypage-container {
            max-width: 800px;
            margin: auto;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            overflow: hidden;
        }

        .mypage-header {
            background-color: #393939;
            color: white;
            padding: 20px;
            text-align: center;
        }

        .mypage-menu {
            display: flex;
            justify-content: space-around;
            background-color: #f1f1f1;
            padding: 10px;
        }

        .mypage-menu button {
            padding: 10px;
            font-size: 16px;
            cursor: pointer;
            border: none;
            background-color: inherit;
            color: #4CAF50;
        }

        .mypage-content {
            padding: 20px;
        }
        .input_area {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 10px;
        }

        .input_area label {
            flex-grow: 1;
            margin-right: 10px;
        }

        .input_area button {
            padding: 5px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }
    </style>
</head>
<body>

<div class="mypage-container">
    <div class="mypage-header">
        <h1>마이페이지</h1>
    </div>

    <div class="mypage-menu">
        <a href="/user/profileUpdate.do">회원정보수정</a>
        <a href="/orderHistory.do">주문 명세 조회</a>
        <a href="/pointHistory">포인트 사용내력 조회</a>
        <a href="/user/addressManage.do">주소 관리</a>
    </div>

    <div class="mypage-content" id="mypage-content">
    </div>
</div>



<div class="input_area">
    <h4>* 아이디</h4>
    <c:choose>
        <c:when test="${empty sessionScope.user_id}">
            <label>${user.userId}</label>
        </c:when>
        <c:otherwise>
            <label>${user.userId}</label>
        </c:otherwise>
    </c:choose>
</div>

<div class="input_area">
    <h4>* 이름</h4>
    <label>${user.userName}</label>
</div>

<div class="input_area">
    <h4>* 생년월일</h4>
    <label>${user.userBirth}</label>
</div>


<script>
    function showUserInfo() {
        window.location.href = '/user/update';
    }

    function showOrderHistory() {
        window.location.href = '/order/history';
    }

    function showPointHistory() {
        window.location.href = '/point/history';
    }

    function showAddressManagement() {
        window.location.href = '/address/manage';
    }
</script>

</body>
</html>
