<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset Your Password</title>
    </head>
    <body>
        <h1>Reset your password</h1>
        <s:form action="UserPasswordReset">
            <s:hidden name="token" value="%{token}"/>
            <s:password name="password" label="Password"/>
            <s:password name="passwordAgain" label="Password again"/>
            <s:submit value="reset"/>
        </s:form>
    </body>
</html>
