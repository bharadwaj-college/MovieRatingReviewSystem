<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login - Movie Rating System</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #eef2f7;
            margin: 0;
        }

        .container {
            width: 380px;
            margin: 90px auto;
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 15px #cccccc;
        }

        h2 {
            text-align: center;
            color: #243b55;
        }

        input {
            width: 100%;
            padding: 12px;
            margin: 12px 0;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        button {
            width: 100%;
            padding: 12px;
            background: #243b55;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        button:hover {
            background: #141e30;
        }

        .error {
            color: red;
            text-align: center;
        }

        .success {
            color: green;
            text-align: center;
        }

        .link {
            text-align: center;
            margin-top: 15px;
        }
    </style>
</head>

<body>

<div class="container">

    <h2>Movie Rating System</h2>
    <h3 style="text-align:center;">User Login</h3>

    <c:if test="${not empty error}">
        <p class="error"><c:out value="${error}"/></p>
    </c:if>

    <c:if test="${not empty success}">
        <p class="success"><c:out value="${success}"/></p>
    </c:if>

    <form action="${pageContext.request.contextPath}/login"
          method="post">

        <input type="email"
               name="email"
               placeholder="Email Address"
               required>

        <input type="password"
               name="password"
               placeholder="Password"
               required>

        <button type="submit">Login</button>

    </form>

    <div class="link">
        New user?
        <a href="${pageContext.request.contextPath}/register">
            Create Account
        </a>
    </div>

</div>

</body>
</html>
