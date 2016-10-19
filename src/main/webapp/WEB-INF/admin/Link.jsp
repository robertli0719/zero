<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="initial-scale=1.0, width=device-width, user-scalable=yes" />
        <meta name="image-action-url" content="<s:property value="image_action_url"/>" />

        <link rel="stylesheet" href="../plugin/bootstrap-3.3.7/css/bootstrap.min.css" type="text/css"/>
        <link rel="stylesheet" href="../plugin/cropper/cropper.min.css" type="text/css"/>
        <script src='../plugin/jquery/jquery-1.11.3.min.js'></script>
        <script src='../plugin/bootstrap-3.3.7/js/bootstrap.min.js'></script>
        <script src='../plugin/cropper/cropper.min.js'></script>
        <script src='../js/common/components.js'></script>
        <script src='../js/admin/Link.js'></script>
        <title>Link Configuration Management</title>
    </head>
    <body>
        <jsp:include page="nav_bar.jsp"/>
        <div class="container">
            <h2>Link Configuration</h2>
            <div class="row">
                <div class="col-md-4">
                    <s:actionmessage theme="bootstrap"/>
                    <s:actionerror theme="bootstrap"/>
                    <div class="panel panel-primary">
                        <div class="panel-heading">Select Domain</div>
                        <div class="panel-body">
                            <s:form action="Link!listPageName" method="get" theme="bootstrap">
                                <s:select id="select_namespace" name="linkGroup.namespace" list="namespaceList" label="namespace"/>
                                <s:submit value="list page name" cssClass="btn"/>
                            </s:form>
                            <s:if test="pageNameList!=null">
                                <s:form action="Link!listName" theme="bootstrap">
                                    <input type="hidden" name="linkGroup.namespace" value="<s:property value="linkGroup.namespace"/>">
                                    <s:select id="select_page_name" name="linkGroup.pageName" list="pageNameList" label="page name"/>
                                    <s:submit value="list name" cssClass="btn"/>
                                </s:form>
                            </s:if>
                            <s:if test="nameList!=null">
                                <s:form theme="bootstrap">
                                    <s:select id="select_name" name="linkGroup.name" list="nameList" label="name"/>
                                </s:form>
                                <button class="btn btn-success" data-cmd="listLink">List</button>
                            </s:if>
                        </div>

                    </div>
                    <div class="panel panel-primary">
                        <div class="panel-heading">Add Link Group</div>
                        <div class="panel-body">
                            <s:form action="Link!addLinkGroup" method="post" theme="bootstrap">
                                <s:textfield name="linkGroup.namespace" label="namespace"/>
                                <s:textfield name="linkGroup.pageName" label="pageName"/>
                                <s:textfield name="linkGroup.name" label="name"/>
                                <s:textfield name="linkGroup.comment" label="comment"/>
                                <s:textfield name="linkGroup.picWidth" label="width" tooltip="The width of the image for the link"/>
                                <s:textfield name="linkGroup.picHeight" label="height" tooltip="The height of the image for the link"/>
                                <s:submit value="add" cssClass="btn"/>
                            </s:form>
                        </div>
                    </div>
                </div>
                <div class="col-md-8" id="link_group_panel"></div>
            </div>
        </div>
    </body>
</html>
