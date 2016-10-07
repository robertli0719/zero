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
        <script src='../plugin/tinymce/tinymce.min.js'></script>
        <script src='../js/common/components.js'></script>
        <script src='../js/admin/editor.js'></script>
        <script src='../js/admin/PageUpdate.js'></script>
        <title>Page Management</title>
    </head>
    <body>
        <jsp:include page="nav_bar.jsp"/>
        <div class="container">
            <s:textfield label="Id" name="id" disabled="true" theme="bootstrap"/>
            <s:textfield label="Category" name="category" disabled="true" theme="bootstrap"/>
            <s:textfield label="Title" name="title" theme="bootstrap"/>
            <s:textfield label="Description" name="description" theme="bootstrap"/>
            <a href="#" class="btn btn-primary btn-sm" data-cmd="addImageToEditor">Add Image</a>
            <a href="#" class="btn btn-primary btn-sm" data-cmd="uploadCroppedImageToEditor">Add & Crop Image</a>
            <a href="#" class="btn btn-primary btn-sm" data-cmd="update">update</a>
            <hr>
            <textarea><s:property value="content"/></textarea>
        </div>
    </body>
</html>
