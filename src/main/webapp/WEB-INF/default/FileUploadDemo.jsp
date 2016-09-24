<%-- 
    Document   : FileUploadDemo
    Created on : Sep 24, 2016, 2:16:26 AM
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
        <h1>File Upload Demo</h1>
        <s:form action="FileUploadDemo" method="post" enctype="multipart/form-data">
            <s:file name="fe" label="File"/>
            <s:submit />
        </s:form>
    </body>
</html>
