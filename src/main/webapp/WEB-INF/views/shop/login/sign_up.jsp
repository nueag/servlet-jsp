<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kaeun
  Date: 2023/12/03
  Time: 5:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
    #passwordMessage {
        color: green;
    }

    #passwordMessage.invalid {
        color: red;
    }
</style>

<div class="outer">
    <div id="joinInfoArea">
        <form id="joinForm" action="<%= (session.getAttribute("user_id") != null) ? "/profileUpdatePost.do" : "/signUpAction.do" %>" method="post">
            <h1><%= (session.getAttribute("user_id") != null) ? "회원 정보 수정" : "회원 가입" %></h1>


            <h4>* 아이디</h4>
            <c:choose>
                <c:when test="${empty sessionScope.user_id}">
                    <span class="input_area" ><input type="text" name="userId" id="userId" value="${user.userId}" maxlength="13" readonly required ></span>
                    <input type="button" onclick="idCheck();" value="ID 중복확인" >
                </c:when>
                <c:otherwise>
                    <span class="input_area" ><input type="text" name="userId" id="userIdRead" value="${user.userId}" maxlength="13" readonly required ></span>
                </c:otherwise>
            </c:choose>
            <h4>* 비밀번호</h4>
            <span class="input_area"><input type="password" name="userPwd" id="password1" value="${user.userPassword}" maxlength="15" onkeyup="checkPassword()" required></span>

            <h4>* 비밀번호 확인</h4>
            <span class="input_area"><input type="password" name="userPwd_re" id="password2"  value="${user.userPassword}" maxlength="15" onkeyup="checkPassword()" required></span>
            <label id="pwdResult"></label>
            <span id="passwordMessage"></span><br>

            <h4>* 이름</h4>
            <span class="input_area"><input type="text" maxlength="5" name="userName" value="${user.userName}" required></span>

            <h4>* 생년월일</h4>
            <span class="input_area"><input type="text" name="birth" value="${user.userBirth}" placeholder="YYYYMMDD"></span>

            <div class="btnArea">
                <button id="joinBtn"><%= (session.getAttribute("user_id") != null) ? "수정하기" : "가입하기" %></button>
            </div>
            <% if (request.getAttribute("isFinished") != null) { %>
            <p style="color: red;"><%= "비밀번호가 일치하는지 확인하세요"%></p>
            <% } %>

        </form>
    </div>
</div>

<script>
    function idCheck(){
        window.open("/idCheck.do","_blank","width=500px height=500px");
    }
</script>
<script>
    function checkPassword() {
        let password = document.getElementById("password1").value;
        let confirmPassword = document.getElementById("password2").value;

        if (password === confirmPassword) {
            document.getElementById("passwordMessage").innerHTML = "비밀번호 일치";
            passwordMessage.className = ""; // 클래스를 비워서 불일치 색상을 없앰
        } else {
            document.getElementById("passwordMessage").innerHTML = "비밀번호 불일치";
            passwordMessage.className = "invalid"; // 불일치 색상을 추가
        }
    }
</script>
