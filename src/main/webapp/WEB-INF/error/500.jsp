<%-- 
    Document   : 500
    Created on : Sep 15, 2016, 5:44:35 PM
    Author     : Robert Li
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
    </head>
    <body>
        <h1>500 Internal Server Error</h1>
        <p>Sorry an exception occured!  </p>
        <form>  
            <input type="button" value="back" onclick="history.back()">  
        </form>  
        <h2>Please send the message below to our programmers to fix that.</h2>
        <p>Exception Name: <s:property value="exception" /> </p>  
        <p>Exception Details: <s:property value="exceptionStack" /></p>  

    </body>
</html>
