<%-- 
    Document   : PageAdd
    Created on : Oct 3, 2016, 12:48:38 AM
    Author     : Robert Li
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="initial-scale=1.0, width=device-width, user-scalable=yes" />
        <meta name="image-action-url" content="<s:property value="image_action_url"/>" />

        <link rel="stylesheet" href="../plugin/bootstrap-3.3.7/css/bootstrap.min.css" type="text/css"/>
        <script src='../plugin/jquery/jquery-1.11.3.min.js'></script>
        <script src='../plugin/bootstrap-3.3.7/js/bootstrap.min.js'></script>
        <script src='../plugin/tinymce/tinymce.min.js'></script>
        <script src='../js/admin/editor.js'></script>
        <script src='../js/admin/PageAdd.js'></script>
        <title>Page Management</title>
    </head>
    <body>
        <jsp:include page="nav_bar.jsp"/>
        <jsp:include page="add_image_modal.jsp"/>

        <div class="container">
            <s:select label="Category" name="category" list="pageCategoryNameList" theme="bootstrap"/>
            <s:textfield label="Title" name="title" theme="bootstrap"/>
            <s:textfield label="Description" name="description" theme="bootstrap"/>
            <a href="#" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addImageModal">Add Image</a>
            <a href="#" class="btn btn-primary btn-sm" data-cmd="submit">submit</a>
            <hr>
            <textarea></textarea>
        </div>
    </body>
</html>
