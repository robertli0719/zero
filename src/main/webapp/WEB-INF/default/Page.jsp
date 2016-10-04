<%-- 
    Document   : Page
    Created on : Oct 3, 2016, 8:21:19 PM
    Author     : Robert Li
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="initial-scale=1.0, width=device-width, user-scalable=yes" />
        <link rel="stylesheet" href="plugin/bootstrap-3.3.7/css/bootstrap.min.css" type="text/css"/>
        <script src='plugin/jquery/jquery-1.11.3.min.js'></script>
        <script src='plugin/bootstrap-3.3.7/js/bootstrap.min.js'></script>
        <title><s:property value="page.title"/></title>
    </head>
    <body>
        <jsp:include page="nav_bar.jsp"/>
        <div class="container">
            <div class="row">
                <s:property value="page.content" escape="false"/>
            </div>
        </div>
    </body>
</html>
