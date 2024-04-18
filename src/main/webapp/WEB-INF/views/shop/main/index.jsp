<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nhn
  Date: 2023/11/08
  Time: 10:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>


<h1><strong>  간의 ShoppingMall</strong></h1>

<div class="container-fluid">
    <div class="row">
        <nav id="sidebar" class="col-md-3 col-lg-2 d-md-block bg-light sidebar">
            <div class="position-sticky">
                <ul class="nav flex-column">
                    <li class="nav-item">
                        <a class="nav-link active" href="/index.do?index=1&page=1">
                            뷰티
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/index.do?index=2&page=1">
                            문구/오피스
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/index.do?index=3&page=1">
                            식품
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/index.do?index=4&page=1">
                            크리스마스
                        </a>
                    </li>
                </ul>
            </div>
        </nav>

        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
                <c:forEach var="product" items="${productList}">
                    <div class="col">
                        <div class="card shadow-sm">
                            <img src="${product.productImage}" width="100%" height="100%" />
                            <div class="card-body">
                                <p class="card-text">${product.productName}</p>

                                <div class="d-flex justify-content-between align-items-center">
                                    <div class="btn-group">
                                        <a href="/productDetail.do?productId=${product.productId}" class="btn btn-sm btn-outline-secondary">View</a>
                                    </div>
                                    <small class="text-muted">${product.registerDate}</small>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </main>
    </div>
</div>

<c:if test="${currentPage > 1}">
    <td><a href="index.do?index=${index}&page=${currentPage - 1}">Previous</a></td>
</c:if>
<table >
    <tr>
        <c:forEach begin="1" end="${noOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                    <td><a href="index.do?index=${index}&page=${i}">${i}</a></td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </tr>
</table>

<c:if test="${currentPage lt noOfPages}">
    <td><a href="index.do?index=${index}&page=${currentPage + 1}">Next</a></td>
</c:if>

