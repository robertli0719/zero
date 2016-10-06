/* 
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */

// Common Function
+function () {
    $.randString = function (len) {
        len = len || 32;
        var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';/*not includes:oOLl,9gq,Vv,Uu,I1*/
        var maxPos = $chars.length;
        var text = '';
        for (var i = 0; i < len; i++) {
            text += $chars.charAt(Math.floor(Math.random() * maxPos));
        }
        return text;
    };

    $.getUploadUrl = function () {
        var imageActionUrl = $("meta[name=image-action-url]").attr("content");
        if (imageActionUrl === undefined) {
            console.log("FileUpload doesn't work.\ntips: you need to set image-action-url in <meta>");
        }
        return imageActionUrl + "!upload";
    };
}($);

// Modal & msg
+function () {

    $.Modal = function (modalId, title, bodyDiv, footerDiv) {
        var modal = $("<div>").addClass("modal fade").attr("id", modalId).attr("tabindex", -1).attr("role", "dialog");
        var modalDialog = $("<div>").addClass("modal-dialog").attr("role", "document").appendTo(modal);
        var modalContent = $("<div>").addClass("modal-content").appendTo(modalDialog);
        var modalHeader = $("<div>").addClass("modal-header").appendTo(modalContent);
        $('<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>').appendTo(modalHeader);
        $("<h4>").addClass("modal-title").html(title).appendTo(modalHeader);
        bodyDiv.addClass("modal-body").appendTo(modalContent);
        footerDiv.addClass("modal-footer").appendTo(modalContent);
        return modal.appendTo("body");
    };

    $.MsgModal = function (modalId, message) {
        var bodyDiv = $("<div>");
        $("<p>").html(message).appendTo(bodyDiv);
        var footerDiv = $("<div>");
        $("<button>").attr("type", "button").addClass("btn btn-primary").attr("data-dismiss", "modal").html("OK").appendTo(footerDiv);
        return new Modal(modalId, "Info", bodyDiv, footerDiv);
    };

    $.msg = function (message) {
        var id = "msg_" + $.randString(32);
        $.MsgModal(id, message);
        $('#' + id).modal();
    };

}($);

// Image Upload
+function () {
    var files = null;
    var uploadUrl = $.getUploadUrl();
    var modaId = "modal_" + $.randString(32);
    var modaIdChooser = "#" + modaId;
    var _width = 16;
    var _height = 9;

    function ImageUploadModal(modalId) {
        var bodyDiv = $("<div>");
        var fileInputLabel = $("<label>").addClass("btn btn-default btn-file").html("Browse").appendTo(bodyDiv);
        $("<input>").attr("type", "file").attr("data-cmd", "upload-image").attr("name", "fe").appendTo(fileInputLabel);

        var footerDiv = $("<div>");
        $("<button>").attr("type", "button").addClass("btn btn-default").attr("data-dismiss", "modal").html("Close").appendTo(footerDiv);
        $("<button>").attr("type", "button").addClass("btn btn-primary").attr("data-cmd", "upload").html("upload").appendTo(footerDiv);
        return $.Modal(modalId, "Image Upload", bodyDiv, footerDiv);
    }

    function makeCropper(tagId) {
        var image = document.getElementById(tagId);
        var cropper = new Cropper(image, {
            aspectRatio: _width / _height,
            crop: function (e) {
                console.log(e.detail.x);
                console.log(e.detail.y);
                console.log(e.detail.width);
                console.log(e.detail.height);
                console.log(e.detail.rotate);
                console.log(e.detail.scaleX);
                console.log(e.detail.scaleY);
            }
        });
    }

    function uploadCallback(val) {
        var obj = JSON.parse(val);
        var result = obj["result"];
        if (result === "fail") {
            var error = obj["errorString"];
            alert(error);
            console.log(error);
            return;
        }

        $(modaIdChooser + " .modal-body").empty().append($("<div>").addClass("img_content"));
        var urlList = obj["urlList"];
        for (var id in urlList) {
            var url = urlList[id];
            console.log(url);
            var tagId = "image_" + $.randString(32);
            $("<img>").attr("id", tagId).attr("src", url).addClass("img-responsive").appendTo(".img_content");
            makeCropper(tagId);
        }
    }

    function upload() {
        if (files === null) {
            return;
        }
        var data = new FormData();
        $.each(files, function (key, value) {
            console.log(key + "->" + value);
            data.append("img", value);
        });
        $.ajax({
            url: uploadUrl,
            type: 'POST',
            data: data,
            processData: false,
            contentType: false,
            success: function (result) {
                files = null;
                $(modaIdChooser + " [data-cmd=upload-image]").val(null);
                console.log("\n\n RESSULT:");
                uploadCallback(result);
            },
            error: function (result) {
                console.log("error");
                console.log(result);
                alert(result);
            }
        });
    }

    $.uploadImage = function (width, height, callback) {
        _width = width;
        _height = height;

        new ImageUploadModal(modaId);
        $(modaIdChooser + " [data-cmd=upload-image]").on("change", function (event) {
            files = event.target.files;
        });
        $(modaIdChooser + " [data-cmd=upload]").on("click", upload);
        $(modaIdChooser).modal();
        callback(null);
    };
}($);


/************** DEMO ******************/
//$(function () {
//    var bodyDiv = $("<div>");
//    var footerDiv = $("<div>");
//    var m = $.Modal("zzz", "test demo", bodyDiv, footerDiv);
//    $('#' + "zzz").modal();
//});

//$(function () {
//    $.uploadImage(800, 600, function (url) {
//        console.log(url);
//    });
//});