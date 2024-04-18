<%--
  Created by IntelliJ IDEA.
  User: kaeun
  Date: 2023/12/05
  Time: 3:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>학생-조회</title>
    <html>
    <head>
        <title>학생-조회</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
                padding: 20px;
                background-color: #f5f5f5;
            }

            form {
                width: 50%;
                margin: 0 auto;
                background-color: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            table {
                width: 100%;
                margin-bottom: 20px;
                border-collapse: collapse;
            }

            th, td {
                padding: 10px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }

            th {
                background-color: #f2f2f2;
            }

            input[type="text"] {
                width: 100%;
                padding: 8px;
                margin-top: 6px;
                margin-bottom: 16px;
                box-sizing: border-box;
            }

            button {
                background-color: #4CAF50;
                color: white;
                padding: 10px 15px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }

            button:hover {
                background-color: #45a049;
            }

            ul {
                list-style: none;
                padding: 0;
            }

            li {
                display: inline-block;
                margin-right: 10px;
            }

            a {
                text-decoration: none;
                color: #333;
            }
        </style>
    </head>
    <body>


    </body>
    </html>

</head>
<body>

<form method="post" action="/admin/updateProduct.do">
    <table>
        <tbody>
        <tr>
            <th>상품 아이디</th>
            <td><input type="text" name="productId" value="${product.productId}" readonly/></td>
        </tr>
        <tr>
            <th>상품명</th>
            <td><input type="text" name="productName" value="${product.productName}" required/></td>
        </tr>
        <tr>
            <th>가격</th>
            <td><input type="text" name="price" value="${product.price}" required/></td>
        </tr>
        <tr>
            <th>재고</th>
            <td><input type="text" name="stock" value="${product.stock}" required/></td>
        </tr>
        <tr>
            <th>카테고리</th>
            <td>
                <c:forEach var="category" items="${categoryList}">
                    <label for="category-${category.categoryId}">
                        <input type="checkbox" id="category-${category.categoryId}" name="category"
                               value="${category.categoryId}" onclick="limitCategorySelection()">
                            ${category.categoryName}
                    </label>
                </c:forEach>
                <script>
                    function limitCategorySelection() {
                        let checkboxes = document.querySelectorAll('input[name="category"]:checked');

                        if (checkboxes.length > 3) {
                            alert("최대 3개의 카테고리만 선택할 수 있습니다.");
                            checkboxes[checkboxes.length - 1].checked = false;
                        }
                    }
                </script>
            </td>
        </tr>
        <tr>
            <th>상품이미지</th>
            <td><input type="text" name="productImage" value="${product.productImage}" required/></td>
        </tr>
        <tr>
            <th>상세정보</th>
            <td><input type="text" name="productInfo" value="${product.productInfo}" required/></td>
        </tr>
        </tbody>
    </table>
    <p>
        <button type="submit">수정</button>
    </p>

</form>
<ul>
    <li><a href="/admin/productManagement.do?page=1">리스트</a></li>
    <li>
        <form method="post" action="/admin/deleteProduct.do">
            <button name="productId" value="${product.productId}">삭제</button>
        </form>
    </li>
</ul>

</body>
</html>