<%-- 
    Document   : add_image_modal
    Created on : Oct 4, 2016, 9:38:29 PM
    Author     : Robert Li
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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