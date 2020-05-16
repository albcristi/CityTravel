<%--
  Created by IntelliJ IDEA.
  User: albcr
  Date: 15/05/2020
  Time: 6:03 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SignUp</title>
    <link rel="stylesheet" type="text/css" href="sign-up.css">
    <script src="../jquery-2.0.3.js"></script>
    <script src="sign-up.js"></script>
</head>
<body>
<p id="sgn-up-enter-msg">Become a Member</p>
<div class="sign-up-container">
    <div id="sgn-up-usr-name-container">
        <p id="sgn-up-user-name-p">User-Name</p>
        <input id="sgn-up-user-name-input">
    </div>
    <div id="sgn-up-usr-password-container">
        <p id="sgn-up-usr-password-p">Password</p>
        <input type="password" id="sgn-up-usr-password-input">
    </div>
    <div id="sgn-up-button-container">
        <button id="sgn-up-button">Submit</button>
    </div>
    <div id="sgn-up-invalidUserData">
        <p>Invalid Username</p>
    </div>
    <div id="sgn-up-invalid-length">
        <p>Username and password<br/>
        should have length > 3</p>
    </div>
</div>
</body>
</html>
