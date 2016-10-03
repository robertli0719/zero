/* 
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
/* global tinymce, tinyMCE */

tinymce.init({
    selector: 'textarea',
    height: 500,
    plugins: [
        'advlist autolink lists link image charmap print preview anchor',
        'searchreplace visualblocks code fullscreen',
        'insertdatetime media table contextmenu paste code'
    ],
    toolbar: 'insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image',
    content_css: '//www.tinymce.com/css/codepen.min.css'
});

function insertImageToEditor(url) {
    var tag = '<img src="' + url + '">';
    tinymce.activeEditor.execCommand('mceInsertContent', false, tag);
}

function getUploadUrl() {
    var imageActionUrl = $("meta[name=image-action-url]").attr("content");
    if (imageActionUrl === undefined) {
        console.log("FileUpload doesn't work.");
        console.log("tips: you need to set image-action-url in <meta>");
    }
    return imageActionUrl + "!upload";
}

function uploadImageCallback(result) {
    var obj = JSON.parse(result);
    var result = obj["result"];
    if (result === "fail") {
        var error = obj["errorString"];
        alert(error);
        console.log(error);
        return;
    }
    var urlList = obj["urlList"];
    for (var id in urlList) {
        var url = urlList[id];
        console.log(url);
        insertImageToEditor(url);
    }
    $('#addImageModal').modal('hide');
}

$(function () {
    var files = null;
    var uploadUrl = getUploadUrl();
    var imageInput = "#addImageModal [data-input=image]";

    $(document).on("click", "[data-cmd=run]", function () {
        tinymce.activeEditor.execCommand('mceInsertContent', false, "some text");
        console.debug(tinyMCE.activeEditor.getContent());
    });

    $(document).on("change", imageInput, function (event) {
        files = event.target.files;
    });

    $(document).on("click", "#addImageModal [data-cmd=upload]", function () {
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
                $(imageInput).val(null);
                uploadImageCallback(result);
            },
            error: function (result) {
                console.log("Error" + result);
                alert(result);
            }
        });
    });
});