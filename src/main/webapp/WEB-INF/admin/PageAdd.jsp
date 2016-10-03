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
        <script src='../js/admin/PageAdd.js'></script>
        <title>Page Management</title>
    </head>
    <body>
        <jsp:include page="nav_bar.jsp"/>

        <div class="modal fade" id="addImageModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">Add Image</h4>
                    </div>
                    <div class="modal-body">
                        <input type="file" data-input="image"/>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" data-cmd="upload">upload</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="container">
            <a href="#" class="btn btn-primary btn-sm" data-cmd="run">run</a>
            <a href="#" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addImageModal">Add Image</a>
            <hr>
            <textarea></textarea>
        </div>
    </body>
</html>
