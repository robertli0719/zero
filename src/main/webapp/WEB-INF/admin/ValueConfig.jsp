<%-- 
    Document   : ValueConfig
    Created on : Oct 5, 2016, 12:06:44 AM
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
        <script src='../js/admin/ValueConfig.js'></script>
        <title>Value Configuration Management</title>
    </head>
    <body>
        <jsp:include page="nav_bar.jsp"/>
        <div class="container">
            <h2>Value Configuration</h2>
            <div class="row">
                <div class="col-md-4">
                    <s:actionmessage theme="bootstrap"/>
                    <s:actionerror theme="bootstrap"/>
                    <div class="panel panel-primary">
                        <div class="panel-heading">Select Name Space</div>
                        <div class="panel-body">
                            <s:form action="ValueConfig!listPageName" method="get" theme="bootstrap">
                                <s:select id="select_namespace" name="valueConfig.namespace" list="namespaceList" label="namespace"/>
                                <s:submit value="list page name" cssClass="btn"/>
                            </s:form>
                            <s:if test="pageNameList!=null">
                                <s:form theme="bootstrap">
                                    <s:select id="select_page_name" name="valueConfig.pageName" list="pageNameList" label="page name"/>
                                </s:form>
                                <button class="btn btn-success" data-cmd="listValueConfig">List</button>
                            </s:if>
                        </div>
                    </div>
                    <div class="panel panel-primary">
                        <div class="panel-heading">Insert Value</div>
                        <div class="panel-body">
                            <s:form action="ValueConfig!insert" method="post" theme="bootstrap">
                                <s:textfield name="valueConfig.namespace" label="namespace"/>
                                <s:textfield name="valueConfig.pageName" label="pageName"/>
                                <s:textfield name="valueConfig.name" label="name"/>
                                <s:textfield name="valueConfig.val" label="val"/>
                                <s:submit value="add" cssClass="btn"/>
                            </s:form>
                        </div>
                    </div>
                </div>
                <div class="col-md-8">
                    <s:if test="valueConfig.namespace!=null">
                        <div class="table_div">
                            <table class="table table-bordered table-condensed" id="value_config_form_table"></table>
                            <hr>
                            <button class="btn btn-primary" data-cmd="addLine" disabled="true">add line</button>
                            <button class="btn btn-danger" data-cmd="deleteLine" disabled="true">remove line</button>
                            <button class="btn btn-success" data-cmd="submit" disabled="true">submit</button>
                        </div>
                    </s:if>
                </div>
            </div>
        </div>
    </body>
</html>
