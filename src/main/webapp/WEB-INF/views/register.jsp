<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Register - Movie Rating System</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #eef2f7;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 380px;
            margin: 70px auto;
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
            margin: 10px 0;
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

        .message {
            color: red;
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

    <h2>Create Account</h2>

    <c:if test="${not empty error}">
        <p class="message"><c:out value="${error}"/></p>
    </c:if>

    <form action="${pageContext.request.contextPath}/register"
          method="post">

        <input type="text"
               name="name"
               placeholder="Full Name"
               maxlength="100"
               required>

        <input type="email"
               name="email"
               placeholder="Email Address"
               maxlength="100"
               required>

        <input type="password"
               name="password"
               placeholder="Password (minimum 8 characters)"
               minlength="8"
               required>

        <input type="password"
               name="confirmPassword"
               placeholder="Confirm Password"
               minlength="8"
               required>

        <button type="submit">Register</button>

    </form>

    <div class="link">
        Already have an account?
        <a href="${pageContext.request.contextPath}/login">
            Login here
        </a>
    </div>

</div>

</body>
</html>
