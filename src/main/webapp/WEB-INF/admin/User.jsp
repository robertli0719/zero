<%-- 
    Document   : User
    Created on : Sep 27, 2016, 11:30:07 PM
    Author     : Robert Li
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="z" uri="/WEB-INF/tlds/zero" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="initial-scale=1.0, width=device-width, user-scalable=yes" />
        <link rel="stylesheet" href="../plugin/bootstrap-3.3.7/css/bootstrap.min.css" type="text/css"/>
        <script src='../plugin/jquery/jquery-1.11.3.min.js'></script>
        <script src='../plugin/bootstrap-3.3.7/js/bootstrap.min.js'></script>
        <title>User Management</title>
    </head>
    <body>
        <jsp:include page="nav_bar.jsp"/>
        <div class="container">
            <h2>User Management</h2>
            <div class="row">
                <div class="col-md-8">
                    <div class="panel panel-default">
                        <!-- Default panel contents -->
                        <div class="panel-heading">User List</div>
                        <div class="panel-body">
                            <p>Total users: <s:property value="userSearchResult.count"/></p>
                        </div>
                        <s:include value="user_search_result.jsp"/>
                        <div class="panel-footer text-right">
                            <z:pagination result="userSearchResult" />
                            Total: <s:property value="userSearchResult.pageSize"/> pages
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <s:actionmessage theme="bootstrap"/>
                    <s:actionerror theme="bootstrap"/>
                    <div class="panel panel-primary">
                        <div class="panel-heading">Search User</div>
                        <div class="panel-body">
                            <s:form action="User!search" method="get" theme="bootstrap">
                                <s:textfield name="keyword" label="keyword"/>
                                <s:radio
                                    tooltip="Choose your search field"
                                    label="search field "
                                    labelposition="inline"
                                    list="{'name', 'authorizationId'}"
                                    name="field"
                                    cssErrorClass="foo"
                                    />
                                <s:submit value="search" cssClass="btn"/>
                            </s:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
