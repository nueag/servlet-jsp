<%--
  Created by IntelliJ IDEA.
  User: kaeun
  Date: 2023/12/04
  Time: 8:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>상품등록</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 20px;
        }

        h2 {
            color: #333;
        }

        form {
            width: 50%;
            margin-top: 20px;
        }

        label {
            display: block;
            margin-top: 10px;
        }

        input {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
        }

        #btnSubmit {
            background-color: #007bff;
            color: #fff;
            border: none;
            padding: 10px;
            cursor: pointer;
        }

        #btnSubmit:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<h2>상품등록</h2>
<form action="/admin/add_product_post.do" method="post" enctype="multipart/form-data">
    <label for="productName">상품명:</label>
    <input type="text" id="productName" name="productName" required>

    <label for="price">가격:</label>
    <input type="number" id="price" name="price" required>

    <label for="stock">재고:</label>
    <input type="number" id="stock" name="stock" required>

    <label>카테고리:</label>
    <c:forEach var="category" items="${categoryList}">
        <label for="category-${category.categoryId}">
            <input type="checkbox" id="category-${category.categoryId}" name="category" value="${category.categoryId}"
                   onclick="limitCategorySelection()">
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

    <label for="productImage">상품이미지 URL:</label>
    <input type="file" id="productImage" name="productImage">
    <label for="productInfo">상품정보:</label>
    <textarea id="productInfo" name="productInfo" rows="4" required></textarea>
    <br>

    <button type="submit" id="btnSubmit">등록</button>
</form>

</body>
</html>
