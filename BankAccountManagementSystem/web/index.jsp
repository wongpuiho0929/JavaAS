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
        <title>JSP Page</title>
        <script src="jquery/jquery-1.12.0.js" ></script>
        <script src="jquery/Class.js" ></script>
        <script>
            $(document).ready(function(){
                $("button").click(function(){
                    $.post("/BankAccountManagementSystem/login",{
                        "username":$("#username").val(),
                        "password":$("#password").val()
                    }).done(function(data){
                        alert(JSON.parse(data).login);
                    });
                });
                
            });
        </script>
    </head>
    <body>
        <input type="text" id="username" />
        <input type="password" id="password"/>
        <button>login</button>
    </body>
</html>
