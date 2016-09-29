<%-- 
    Document   : Index
    Created on : Sep 24, 2016, 9:27:46 PM
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
        <title>Admin</title>
    </head>
    <body>
        <jsp:include page="nav_bar.jsp"/>
        <div class="container">
            <h2>Zero Management System</h2>
            <div class="row">
                <div class="col-md-8">
                    <div class="jumbotron">
                        <h1>Welcome, back!</h1>
                        <p><s:property value="#admin.username"/></p>
                        <!--<p><a class="btn btn-primary btn-lg" href="#" role="button">Learn more</a></p>-->
                    </div>
                </div>
                <div class="col-md-4">
                    <s:actionmessage theme="bootstrap"/>
                    <s:actionerror theme="bootstrap"/>
                    <div class="panel panel-primary">
                        <div class="panel-heading">Reset Password</div>
                        <div class="panel-body">
                            <s:form action="Index!resetPassword" method="post" theme="bootstrap">
                                <s:password name="oldPassword" label="password"/>
                                <s:password name="newPassword" label="new password"/>
                                <s:password name="newPasswordAgain" label="new password again"/>
                                <s:submit value="change" cssClass="btn"/>
                            </s:form>
                        </div>
                    </div>
                </div>
            </div>
            <s:debug/>

        </div>
    </body>
</html>
