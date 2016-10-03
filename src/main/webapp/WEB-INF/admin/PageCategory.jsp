<%-- 
    Document   : PageCategory
    Created on : Oct 3, 2016, 12:26:20 AM
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
        <title>Page Category Management</title>
    </head>
    <body>
        <jsp:include page="nav_bar.jsp"/>
        <div class="container">
            <h2>User Management</h2>
            <div class="row">
                <div class="col-md-8">
                    <div class="panel panel-default">
                        <div class="panel-heading">Page Category List</div>
                        <div class="panel-body">
                            <p>Total category <s:property value="pageCategoryList.size()"/></p>
                        </div>
                        <s:include value="page_category_table.jsp"/>
                    </div>
                </div>
                <div class="col-md-4">
                    <s:actionmessage theme="bootstrap"/>
                    <s:actionerror theme="bootstrap"/>
                    <div class="panel panel-primary">
                        <div class="panel-heading">Add Category</div>
                        <div class="panel-body">
                            <s:form action="PageCategory!add" method="post" theme="bootstrap">
                                <s:textfield name="name" label="name"/>
                                <s:textfield name="description" label="description"/>
                                <s:submit value="add" cssClass="btn"/>
                            </s:form>
                        </div>
                    </div>
                    <div class="panel panel-primary">
                        <div class="panel-heading">Delete Category</div>
                        <div class="panel-body">
                            <s:form action="PageCategory!delete" method="post" theme="bootstrap">
                                <s:textfield name="name" label="name"/>
                                <s:submit value="delete" cssClass="btn"/>
                            </s:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
