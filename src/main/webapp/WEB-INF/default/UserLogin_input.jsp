<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"  %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Login Page</title>
    </head>
    <body>
        <h1>User Login</h1>
        <s:actionerror/>
        <s:form action="UserLogin">
            <s:textfield name="authId" label="username/email"/>
            <s:password name="password" label="password"/>
            <s:submit value="login"/>
        </s:form>
        <a href="UserForgetPassword">Forget password</a>
    </body>
</html>
