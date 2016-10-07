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

    $.getImageActionUrl = function () {
        var imageActionUrl = $("meta[name=image-action-url]").attr("content");
        if (imageActionUrl === undefined) {
            console.log("FileUpload doesn't work.\ntips: you need to set image-action-url in <meta>");
        }
        return imageActionUrl;
    };

    function makeFormData(files) {
        var data = new FormData();
        $.each(files, function (key, value) {
            console.log(key + "->" + value);
            data.append("img", value);
        });
        return data;
    }

    function uploadCallback(val, callback) {
        var obj = JSON.parse(val);
        var result = obj["result"];
        if (result === "fail") {
            var error = obj["errorString"];
            alert(error);
            console.log(error);
        } else {
            var urlList = obj["urlList"];
            var url = urlList[0];
            callback(url);
        }
    }

    $._uploadImage = function (files, callback) {
        if (files === null) {
            return;
        }
        $.ajax({
            url: $.getImageActionUrl() + "!upload",
            type: 'POST',
            data: makeFormData(files),
            processData: false,
            contentType: false,
            success: function (result) {
                uploadCallback(result, callback);
            },
            error: function (result) {
                console.log("Error: " + result);
                alert(result);
            }
        });
    };
}($);

// Modal & msg
+function () {

    $.Modal = function (modalId, title, bodyDiv, footerDiv) {
        $("#" + modalId).remove();
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

// $.uploadImage
+function () {
    var files = null;
    var modalId = "modal_" + $.randString(32);
    var modalIdChooser = "#" + modalId;

    function ImageUploadModal(modalId) {
        var bodyDiv = $("<div>");
        var fileInputLabel = $("<label>").addClass("btn btn-default btn-file").html("Browse").appendTo(bodyDiv);
        $("<input>").attr("type", "file").attr("data-cmd", "upload-image").attr("name", "fe").appendTo(fileInputLabel);
        var footerDiv = $("<div>");
        $("<button>").attr("type", "button").addClass("btn btn-default").attr("data-dismiss", "modal").html("Close").appendTo(footerDiv);
        $("<button>").attr("type", "button").addClass("btn btn-primary").attr("data-cmd", "upload").html("Upload").appendTo(footerDiv);
        return $.Modal(modalId, "Image Upload", bodyDiv, footerDiv);
    }

    $.uploadImage = function (callback) {
        new ImageUploadModal(modalId);
        $(modalIdChooser + " [data-cmd=upload-image]").on("change", function (event) {
            files = event.target.files;
        });
        $(modalIdChooser + " [data-cmd=upload]").on("click", function () {
            $._uploadImage(files, function (url) {
                $(modalIdChooser + " [data-cmd=upload-image]").val(null);
                files = null;
                $(modalIdChooser).modal("hide");
                callback(url);
            });
        });
        $(modalIdChooser).modal();
    };
}($);

// $.uploadCroppedImage
+function () {
    var files = null;
    var cropper = null;
    var imageActionUrl = $.getImageActionUrl();
    var modalId = "modal_" + $.randString(32);
    var modalIdChooser = "#" + modalId;
    var _imageId = null;

    function ImageUploadModal(modalId) {
        var bodyDiv = $("<div>");
        var fileInputLabel = $("<label>").addClass("btn btn-default btn-file").html("Browse").appendTo(bodyDiv);
        $("<input>").attr("type", "file").attr("data-cmd", "upload-image").attr("name", "fe").appendTo(fileInputLabel);
        var footerDiv = $("<div>");
        $("<button>").attr("type", "button").addClass("btn btn-default").attr("data-dismiss", "modal").html("Close").appendTo(footerDiv);
        $("<button>").attr("type", "button").addClass("btn btn-primary").attr("data-cmd", "upload").html("Upload").appendTo(footerDiv);
        $("<button>").attr("type", "button").addClass("btn btn-primary").attr("data-cmd", "reset").html("Reset").attr("disabled", true).appendTo(footerDiv);
        $("<button>").attr("type", "button").addClass("btn btn-primary").attr("data-cmd", "use").html("Use").attr("disabled", true).appendTo(footerDiv);
        return $.Modal(modalId, "Image Upload", bodyDiv, footerDiv);
    }

    function cropperListening(e) {
        //console.log(e.detail);
    }

    function toCropperView(url) {
        $(modalIdChooser + " .modal-body").empty().append($("<div>").addClass("img_content"));
        $(modalIdChooser + " [data-cmd=upload]").attr("disabled", true);
        $(modalIdChooser + " [data-cmd=reset]").attr("disabled", false);
        $(modalIdChooser + " [data-cmd=use]").attr("disabled", false);
        var tagId = "image_" + $.randString(32);
        $("<img>").attr("id", tagId).attr("src", url).addClass("img-responsive").appendTo(modalIdChooser + " .img_content");
        var image = document.getElementById(tagId);
        cropper = new Cropper(image, {
            viewMode: 1,
            crop: cropperListening
        });
    }

    function getImageId(url) {
        return url.split("id=")[1].substring(0, 36);
    }

    function reset() {
        cropper.reset();
    }

    function use(callback) {
        var data = cropper.getData(true);
        var cropperUrl = imageActionUrl + "!crop";
        $.post(cropperUrl, {
            id: _imageId,
            x: data.x,
            y: data.y,
            width: data.width,
            height: data.height
        }, function (feedback) {
            var json = JSON.parse(feedback);
            var result = json["result"];
            if (result === "fail") {
                console.log(feedback);
                return;
            }
            $(modalIdChooser).modal("hide");
            callback(json["url"]);
        });
    }

    $.uploadCroppedImage = function (callback) {
        _imageId = null;
        new ImageUploadModal(modalId);
        $(modalIdChooser + " [data-cmd=upload-image]").on("change", function (event) {
            files = event.target.files;
        });
        $(modalIdChooser + " [data-cmd=upload]").on("click", function () {
            $._uploadImage(files, function (url) {
                files = null;
                $(modalIdChooser + " [data-cmd=upload-image]").val(null);
                _imageId = getImageId(url);
                toCropperView(url);
            });
        });
        $(modalIdChooser + " [data-cmd=reset]").on("click", reset);
        $(modalIdChooser + " [data-cmd=use]").on("click", function () {
            use(callback);
        });
        $(modalIdChooser).modal();
    };
}($);

// $.uploadFixedImage
+function () {
    var files = null;
    var cropper = null;
    var imageActionUrl = $.getImageActionUrl();
    var modalId = "modal_" + $.randString(32);
    var modalIdChooser = "#" + modalId;
    var _width = 16;
    var _height = 9;
    var _imageId = null;

    function ImageUploadModal(modalId) {
        var bodyDiv = $("<div>");
        var fileInputLabel = $("<label>").addClass("btn btn-default btn-file").html("Browse").appendTo(bodyDiv);
        $("<input>").attr("type", "file").attr("data-cmd", "upload-image").attr("name", "fe").appendTo(fileInputLabel);
        var footerDiv = $("<div>");
        $("<button>").attr("type", "button").addClass("btn btn-default").attr("data-dismiss", "modal").html("Close").appendTo(footerDiv);
        $("<button>").attr("type", "button").addClass("btn btn-primary").attr("data-cmd", "upload").html("Upload").appendTo(footerDiv);
        $("<button>").attr("type", "button").addClass("btn btn-primary").attr("data-cmd", "reset").html("Reset").attr("disabled", true).appendTo(footerDiv);
        $("<button>").attr("type", "button").addClass("btn btn-primary").attr("data-cmd", "use").html("Use").attr("disabled", true).appendTo(footerDiv);
        return $.Modal(modalId, "Image Upload", bodyDiv, footerDiv);
    }

    function cropperListening(e) {
        //console.log(e.detail);
    }

    function toCropperView(url) {
        $(modalIdChooser + " .modal-body").empty().append($("<div>").addClass("img_content"));
        $(modalIdChooser + " [data-cmd=upload]").attr("disabled", true);
        $(modalIdChooser + " [data-cmd=reset]").attr("disabled", false);
        $(modalIdChooser + " [data-cmd=use]").attr("disabled", false);
        var tagId = "image_" + $.randString(32);
        $("<img>").attr("id", tagId).attr("src", url).addClass("img-responsive").appendTo(modalIdChooser + " .img_content");
        var image = document.getElementById(tagId);
        cropper = new Cropper(image, {
            aspectRatio: _width / _height,
            viewMode: 1,
            crop: cropperListening
        });
    }

    function getImageId(url) {
        return url.split("id=")[1].substring(0, 36);
    }

    function reset() {
        cropper.reset();
    }

    function use(callback) {
        var data = cropper.getData(true);
        var cropperUrl = imageActionUrl + "!fix";
        $.post(cropperUrl, {
            id: _imageId,
            x: data.x,
            y: data.y,
            width: data.width,
            height: data.height,
            fixedWidth: _width,
            fixedHeight: _height
        }, function (feedback) {
            var json = JSON.parse(feedback);
            var result = json["result"];
            if (result === "fail") {
                console.log(feedback);
                return;
            }
            $(modalIdChooser).modal("hide");
            callback(json["url"]);
        });
    }

    $.uploadFixedImage = function (width, height, callback) {
        _width = width;
        _height = height;
        _imageId = null;
        new ImageUploadModal(modalId);
        $(modalIdChooser + " [data-cmd=upload-image]").on("change", function (event) {
            files = event.target.files;
        });
        $(modalIdChooser + " [data-cmd=upload]").on("click", function () {
            $._uploadImage(files, function (url) {
                files = null;
                $(modalIdChooser + " [data-cmd=upload-image]").val(null);
                _imageId = getImageId(url);
                toCropperView(url);
            });
        });
        $(modalIdChooser + " [data-cmd=reset]").on("click", reset);
        $(modalIdChooser + " [data-cmd=use]").on("click", function () {
            use(callback);
        });
        $(modalIdChooser).modal();
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