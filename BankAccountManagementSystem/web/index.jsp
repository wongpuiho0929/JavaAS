<%-- 
    Document   : index
    Created on : 2016年2月20日, 上午01:37:35
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bank Account Managment System</title>
        <script src="jquery/jquery-1.12.0.js" ></script>
        <script src="jquery/Class.js" ></script>
        <script>
            $(document).ready(function () {
                $("#loginForm").submit(function (e) {
                    $.post("/BankAccountManagementSystem/login", {
                        "username": $("#username").val(),
                        "password": $("#password").val()
                    }).done(function (data) {
                        alert(JSON.parse(data).login);
                    });
                });
            });
        </script>
    </head>

    <body>
        <link rel="stylesheet" href="css/login_page_style.css">
        
       
        <div class="login">
            <div class="login-triangle"></div>
            <h2 class="login-header">Log in</h2>
            <form class="login-container" id="loginForm">
                <p><input type="text" placeholder="ID" id="username"></p>
                <p><input type="password" placeholder="Password" id="password"></p>
                <p><input type="submit" value="Log in"></p>
            </form>
        </div>
    </body>
</html>
