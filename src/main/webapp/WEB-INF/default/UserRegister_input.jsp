<%-- 
    Document   : UserRegister_input
    Created on : Sep 20, 2016, 12:58:53 AM
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
        <h1>User Register</h1>
        <s:form action="UserRegister">
            <s:textfield name="email" label="Email*"/>
            <s:textfield name="name" label="Name*"/>
            <s:password name="password" label="Password*"/>
            <s:password name="passwordAgain" label="Password Again*"/>
            <s:textfield name="telephone" label="Telephone"/>
            <s:submit/>
        </s:form>
    </body>
</html>
