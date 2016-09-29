<%-- 
    Document   : Admin
    Created on : Sep 26, 2016, 11:06:02 PM
    Author     : Robert Li
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="initial-scale=1.0, width=device-width, user-scalable=yes" />
        <link rel="stylesheet" href="../plugin/bootstrap-3.3.7/css/bootstrap.min.css" type="text/css"/>
        <script src='../plugin/jquery/jquery-1.11.3.min.js'></script>
        <script src='../plugin/bootstrap-3.3.7/js/bootstrap.min.js'></script>
        <title>Admin Management</title>
    </head
    <body>
        <jsp:include page="nav_bar.jsp"/>
        <div class="container">
            <h2>Admin Management</h2>
            <div class="row">
                <div class="col-md-8">
                    <div class="panel panel-default">
                        <!-- Default panel contents -->
                        <div class="panel-heading">Admin List</div>
                        <div class="panel-body">
                            <p>...</p>
                        </div>
                        <s:include value="admin_table.jsp"/>
                    </div>
                </div>
                <div class="col-md-4">
                    <s:actionmessage theme="bootstrap"/>
                    <s:actionerror theme="bootstrap"/>
                    <div class="panel panel-primary">
                        <div class="panel-heading">Add Admin</div>
                        <div class="panel-body">
                            <s:form action="Admin!add" method="post" theme="bootstrap">
                                <s:textfield name="username" label="username"/>
                                <s:password name="password" label="password"/>
                                <s:submit value="add" cssClass="btn"/>
                            </s:form>
                        </div>
                    </div>
                    <div class="panel panel-primary">
                        <div class="panel-heading">Reset Password</div>
                        <div class="panel-body">
                            <s:form action="Admin!resetPassword" method="post" theme="bootstrap">
                                <s:textfield name="username" label="username"/>
                                <s:password name="password" label="password"/>
                                <s:submit value="reset password" cssClass="btn"/>
                            </s:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
