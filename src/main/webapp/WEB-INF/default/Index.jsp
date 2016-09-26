<%-- 
    Document   : Index
    Created on : Sep 15, 2016, 5:59:38 PM
    Author     : Robert Li
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"  %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <jsp:include page="user_panel.jsp"/>
        <div>
            <h3>Demo</h3>
            <a href="FileUploadDemo">File Upload Demo</a>
            <h3>Links</h3>
            <a href="admin/Index">Admin</a>
        </div>
    </body>
</html>
