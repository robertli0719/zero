<%-- 
    Document   : AdminLogin
    Created on : Sep 25, 2016, 1:46:11 PM
    Author     : Robert Li
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"  %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="initial-scale=1.0, width=device-width, user-scalable=yes" />
        <link rel="stylesheet" href="../plugin/bootstrap-3.3.7/css/bootstrap.min.css" type="text/css"/>
        <script src='../plugin/jquery/jquery-1.11.3.min.js'></script>
        <script src='../plugin/bootstrap-3.3.7/js/bootstrap.min.js'></script>
        <title>Admin Login Page</title>
    </head>
    <body>
        <div class="container">
            <h1>Admin Login Page</h1>
            <div class="row">
                <div class="col-md-12">
                    <s:actionerror theme="bootstrap"/>
                    <s:actionmessage theme="bootstrap"/>
                </div>
            </div>
            <div class="row">
                <div class="col-md-3">
                    <s:form action="AdminLogin" method="post" theme="bootstrap">
                        <s:textfield name="username" label="username"/>
                        <s:password name="password" label="password"/>
                        <s:submit cssClass="btn" value="login"/>
                    </s:form>
                </div>
                
            </div>
        </div>
    </body>
</html>
