<%-- 
    Document   : UserRegister
    Created on : Sep 20, 2016, 12:59:02 AM
    Author     : Robert Li
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Please check your email to validate your account</h1>
        <p>the validate email has been sent to <s:property value="email"/></p>        
        <a href="UserRegister!sendVerifyEmail?email=<s:property value="email"/>">send it again</a>
        <s:actionerror/>

    </body>
</html>
