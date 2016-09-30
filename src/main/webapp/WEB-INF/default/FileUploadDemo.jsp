<%-- 
    Document   : FileUploadDemo
    Created on : Sep 24, 2016, 2:16:26 AM
    Author     : Robert Li
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"  %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="initial-scale=1.0, width=device-width, user-scalable=yes" />
        <meta name="image-action-url" content="<s:property value="image_action_url"/>" />

        <link rel="stylesheet" href="plugin/bootstrap-3.3.7/css/bootstrap.min.css" type="text/css"/>
        <script src='plugin/jquery/jquery-1.11.3.min.js'></script>
        <script src='plugin/bootstrap-3.3.7/js/bootstrap.min.js'></script>
        <script src='js/default/FileUploadDemo.js'></script>
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container">
            <h1>File Upload Demo</h1>

            <div class="panel panel-default">
                <div class="panel-heading">struts form demo</div>
                <div class="panel-body">
                    <p>上传文件至 FileUploadDemoAction</p>
                    <s:form action="FileUploadDemo" method="post" enctype="multipart/form-data">
                        <s:file name="fe" label="File"/>
                        <s:submit />
                    </s:form>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading">jquery form demo</div>
                <div class="panel-body">
                    <p>上传图片至图片服务，服务返回访问路径。</p>
                    <p>(上传有跨域名保护，用IP无效)</p>
                    <input type="file" data-cmd="upload-image" name="fe"/>
                    <a class="btn btn-success" data-cmd="upload">upload</a>
                </div>
            </div>
            <div id="img_content"></div>
        </div>

    </body>
</html>
