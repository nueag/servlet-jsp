<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--<style>--%>
<%--  table {--%>
<%--    width: 80%;--%>
<%--    border-collapse: collapse;--%>
<%--    margin: 20px;--%>
<%--  }--%>

<%--  th, td {--%>
<%--    border: 1px solid #dddddd;--%>
<%--    text-align: left;--%>
<%--    padding: 8px;--%>
<%--  }--%>

<%--  th {--%>
<%--    background-color: #f2f2f2;--%>
<%--  }--%>
<%--</style>--%>
<div class="container-fluid">
  <div class="row">


    <table>
      <thead>
      <tr>
        <th>아이디</th>
        <th>이름</th>
        <th>비밀번호</th>
        <th>생일</th>
        <th>권한</th>
        <th>포인트</th>
        <th>생성날짜</th>
        <th>로그인 날짜</th>
      </tr>
      </thead>

      <tbody>
      <c:forEach var="member" items="${userList}">
        <tr>
          <td>${member.userId}</td>
          <td>${member.userName}</td>
          <td>${member.userPassword}</td>
          <td>${member.userBirth}</td>
          <td>${member.userAuth}</td>
          <td>${member.userPoint}</td>
          <td>${member.createdAt}</td>
          <td>${member.latestLoginAt}</td>
        </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>

<c:if test="${currentPage > 1}">
  <td><a href="/admin/customerManagement.do?page=${currentPage - 1}">Previous</a></td>
</c:if>

<table >
  <tr>
    <c:forEach begin="1" end="${noOfPages}" var="i">
      <c:choose>
        <c:when test="${currentPage eq i}">
          <td>${i}</td>
        </c:when>
        <c:otherwise>
          <td><a href="/admin/customerManagement.do?page=${i}">${i}</a></td>
        </c:otherwise>
      </c:choose>
    </c:forEach>
  </tr>
</table>

<c:if test="${currentPage lt noOfPages}">
  <td><a href="/admin/customerManagement.do?page=${currentPage + 1}">Next</a></td>
</c:if>

