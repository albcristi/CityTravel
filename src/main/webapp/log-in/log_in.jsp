<%--
  Created by IntelliJ IDEA.
  User: albcr
  Date: 15/05/2020
  Time: 5:59 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log In</title>
    <link rel="stylesheet" type="text/css" href="log-in.css">
    <script src="../jquery-2.0.3.js"></script>
    <script src="log-in.js"></script>
</head>
<body>
    <p id="log-enter-msg">Sign In</p>
    <div class="log-in-container">
        <div id="usr-name-container">
            <p id="user-name-p">User-Name</p>
            <input id="user-name-input">
        </div>
        <div id="usr-password-container">
            <p id="usr-password-p">Password</p>
            <input type="password" id="usr-password-input">
        </div>
        <div id="log-in-button-container">
            <button id="log-in-button">Submit</button>
        </div>
        <div id="invalidUserData">
            <p>Invalid Username and/or Password</p>
        </div>
    </div>
</body>
</html>
